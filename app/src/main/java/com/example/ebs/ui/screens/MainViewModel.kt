package com.example.ebs.ui.screens

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.ebs.data.repositories.local.LocalRepository
import com.example.ebs.data.repositories.remote.ebsApi.EBSRepository
import com.example.ebs.data.structure.GoogleProfileFields
import com.example.ebs.data.structure.local.localItems.DeletedScans
import com.example.ebs.data.structure.local.localItems.ViewedArticles
import com.example.ebs.data.structure.remote.ebs.articles.Article
import com.example.ebs.data.structure.remote.ebs.detections.head.Detection
import com.example.ebs.data.structure.remote.ebs.detections.head.ScanResponse
import com.example.ebs.service.auth.AuthManager
import com.example.ebs.service.database.DatabaseManager
import com.example.ebs.ui.navigation.NavigationHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val ebsRepository: EBSRepository,
    private val authManager: AuthManager,
    private val databaseManager: DatabaseManager,
    private val localRepository: LocalRepository
) : ViewModel() {
    private val _navigateTo = MutableStateFlow<String?>(null)
    val navigateTo: StateFlow<String?> = _navigateTo
    fun updateNavigateTo(route: String?) {
        _navigateTo.value = route
    }

    private val _scanResult = MutableStateFlow<String?>(null)
    val scanResult: StateFlow<String?> = _scanResult
    fun updateScanResult(result: String?) {
        _scanResult.value = result
    }

    var firstOpen: Boolean
        get() = savedStateHandle["firstOpen"] ?: true
        set(value) { savedStateHandle["firstOpen"] = value }

    lateinit var navHandler: NavigationHandler
    lateinit var localCred: String
    lateinit var localInfo: GoogleProfileFields

    val authManagerState: AuthManager
        get() = authManager
    private val ebsRepositoryState: EBSRepository
        get() = ebsRepository
    private val databaseManagerState: DatabaseManager
        get() = databaseManager

    val hazeState = HazeState()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _history = MutableStateFlow(listOf(Detection()))
    val history: StateFlow<List<Detection>> = _history
    private val _articles = MutableStateFlow(listOf(Article()))
    val articles: StateFlow<List<Article>> = _articles
    private val _upImage = MutableStateFlow(ScanResponse())
    val upImage: StateFlow<ScanResponse> = _upImage

    private val localHistory: MutableStateFlow<List<Detection>> = MutableStateFlow(listOf(Detection()))
    val localArticles: MutableStateFlow<List<Article>> = MutableStateFlow(listOf(Article()))

    fun initializeNavHandler(navController: NavController) {
        navHandler = NavigationHandler(navController)
    }

    fun updateLocalCred(token: String) {
        localCred = token
    }

    fun getUserData(){
        val userData = authManagerState.getGoogleProfileInfo()
        updateUserInfo(userData ?: GoogleProfileFields())
    }

    fun resetUpImage() {
        _upImage.value = ScanResponse()
    }

    fun updateUserInfo(info: GoogleProfileFields) {
        localInfo = info
    }

    fun refresh() {
        viewModelScope.launch {
            _articles.update { emptyList() }
            _history.update { emptyList() }
            _isLoading.update { true }
            try {
                val jobArticle =
                    viewModelScope.async { loadArticles() }
                val jobHistory =
                    viewModelScope.async { loadHistory() }
                val jobDelay =
                    viewModelScope.async { delay(1000) }
                jobArticle.await()
                jobHistory.await()
                jobDelay.await()
            } finally {
                _isLoading.update { false }
            }
        }
    }

    fun addViewCounter(id: String, viewCount: Int) {
        viewModelScope.launch {
            val viewedArticles =
                localRepository.getAllViewedArticles().first()
            try {
            if (id !in viewedArticles.map { it.viewedArticles }) {
                localRepository.insertViewedArticle(
                    ViewedArticles(0,id)
                )
                databaseManagerState.updateArticleViewCount(
                    id, viewCount
                )
            }
            } catch (e: Exception) {
                Log.e("Scans", "Error updating view count: ${e.message}")
            }
        }
    }

    fun addDeletedScans(id: String) {
        viewModelScope.launch {
            try {
                localRepository.insertDeletedScan(
                    DeletedScans(0,id)
                )
                loadHistory()
            } catch (e: Exception) {
                Log.e("Scans", "Error updating view count: ${e.message}")
            }
        }
    }

    private fun loadArticles() {
        viewModelScope.launch {
            var result = _articles.value
            try {
                result = databaseManagerState.getArticles()
                    .filter {
                        it.status == "published" && it.deletedAt == null
                    }
                    .filter{
                            article -> article.id !in localArticles.value.map{ it.id }
                    }
                if(result.isNotEmpty()) {
                    localArticles.value =
                        result.filter { article -> article.id !in localArticles.value.map { it.id } }
                            .map { it
                            }
                }
            } catch (e: Exception) {
                Log.e("Scans", "Error loading articles: ${e.message}")
            }
            _articles.value = result.ifEmpty {
                localArticles.value
            }
        }
    }

    private fun loadHistory() {
        viewModelScope.launch {
            var result = _history.value
            if(authManagerState.isSignedIn()) {
                try {
                    val userId =
                        authManagerState.getUserId() ?: ""
                    val token =
                        ebsRepositoryState.loginUser(userId)
                    val listHistory =
                        ebsRepositoryState.getHistory(token)
                    val viewedArticles =
                        localRepository.getAllDeletedScans().first()
                    result = listHistory.data
                        .filter { it.id !in viewedArticles.map { scan ->
                            scan.deletedScans }
                        }
                        .filter {
                            scan -> scan.id !in localHistory.value.map{ it.id }
                        }
                        .map {
                            ebsRepositoryState.getDetection(token, it.id)
                        }

                    if(result.isNotEmpty()) {
                        localHistory.value =
                            result.filter { scan -> scan.id !in localHistory.value.map { it.id } }
                                .map { it }
                    }
                } catch (e: Exception) {
                    Log.e("Scans", "Error loading histories: ${e.message}")
                }
                if (result.isEmpty()) {
                    result = localHistory.value
                }
            }
            _history.value = result
        }
    }

    suspend fun uploadImage(filePath: String) {
        val userId = authManagerState.getUserId()
        val token =
            ebsRepositoryState.loginUser(
                userId ?: ""
            )
        val result =
            ebsRepositoryState.uploadImage(
                userId ?: "", filePath, token
            )
        _upImage.value = result
    }

    suspend fun pollResultOnce(id: String): Detection {
        return try {
            val userId = authManagerState.getUserId() ?: ""
            val token = ebsRepositoryState.loginUser(userId)
            ebsRepositoryState.getDetection(token, id)
        } catch (e: Exception) {
            Log.e("Scans", "Error polling result: ${e.message}")
            Detection()
        }
    }

    suspend fun pollResult(id: String): Detection {
        return try {
            val userId = authManagerState.getUserId() ?: ""
            val token = ebsRepositoryState.loginUser(userId)
            val result = ebsRepositoryState.getDetection(token, id)
//            if (result.status == "completed" && tries > 1) {
//                eBSNotificationService.showExpandableResultNotification(result.imageUrl,result.id)
//            }
            result
        } catch (e: Exception) {
            Log.e("Scans", "Error polling result: ${e.message}")
            Detection().copy(
                status = "completed",
                objectsCount = 1
            )
        }
    }
}