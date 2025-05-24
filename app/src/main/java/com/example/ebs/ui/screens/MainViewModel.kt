package com.example.ebs.ui.screens

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.ebs.data.repositories.remote.ebsApi.EBSRepository
import com.example.ebs.data.structure.GoogleProfileFields
import com.example.ebs.data.structure.remote.ebs.articles.Article
import com.example.ebs.data.structure.remote.ebs.detections.DataDetections
import com.example.ebs.data.structure.remote.ebs.detections.Histories
import com.example.ebs.service.auth.AuthManager
import com.example.ebs.service.database.DatabaseManager
import com.example.ebs.ui.navigation.NavigationHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val ebsRepository: EBSRepository,
    private val authManager: AuthManager,
    private val databaseManager: DatabaseManager
) : ViewModel() {
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
    private val _history = MutableStateFlow(listOf(Histories()))
    val history: StateFlow<List<Histories>> = _history
    private val _articles = MutableStateFlow(listOf(Article()))
    val articles: StateFlow<List<Article>> = _articles

    private val _upImage = MutableStateFlow(DataDetections())
    val upImage: StateFlow<DataDetections> = _upImage

    fun initializeNavHandler(navController: NavController) {
        navHandler = NavigationHandler(navController)
    }

    fun updateLocalCred(token: String) {
        localCred = token.toString()
    }

    private fun updateUserInfo(info: GoogleProfileFields) {
        localInfo = info
    }

    fun getUserData(){
        updateUserInfo(authManagerState.getGoogleProfileInfo() ?:
        GoogleProfileFields())
    }

    fun refresh() {
        viewModelScope.launch {
            _articles.update { emptyList() }
            _history.update { emptyList() }
            _isLoading.update { true }
            try {
                val jobArticle = viewModelScope.async { loadArticles() }
                val jobHistory = viewModelScope.async { loadHistory() }
                val jobDelay = viewModelScope.async { delay(1000) }
                jobArticle.await()
                jobHistory.await()
                jobDelay.await()
            } finally {
                _isLoading.update { false }
            }
        }
    }

    suspend fun uploadImage(filePath: String) {
        val userId = authManagerState.getUserId()
        val token = ebsRepositoryState.loginUser(userId ?: "")
        val result = ebsRepositoryState.uploadImage(userId ?: "", filePath, token)
        _upImage.value = result
    }

    private fun loadArticles() {
        viewModelScope.launch {
            var result = _articles.value
            try {
                result = databaseManagerState.getArticles()
            } catch (e: Exception) {
                Log.e("Scans", "Error loading articles: ${e.message}")
            }
            _articles.value = result.ifEmpty { listOf(Article()) }
        }
    }

    private fun loadHistory() {
        viewModelScope.launch {
            var result = _history.value
            try {
                val userId = authManagerState.getUserId()
                val token = ebsRepositoryState.loginUser(userId ?: "")
                result = ebsRepositoryState.getHistory(token, userId ?: "")
            } catch (e: Exception) {
                Log.e("Scans", "Error loading histories: ${e.message}")
            }
            _history.value = result.ifEmpty { listOf(Histories()) }
        }
    }
}