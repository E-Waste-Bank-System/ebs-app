package com.example.ebs.ui.face.dashboard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ebs.data.repositories.remote.DataTestRepository
import com.example.ebs.data.structure.Articles
import com.example.ebs.data.structure.Requests
import com.example.ebs.service.AuthManager
import com.example.ebs.service.DatabaseManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val itemsRepository: DataTestRepository,
    private val authManager: AuthManager,
    private val databaseManager: DatabaseManager
) : ViewModel() {
    val hazeState = HazeState()
    val authManagerState: AuthManager
        get() = authManager
    val databaseManagerState: DatabaseManager
        get() = databaseManager

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _requests = MutableStateFlow<List<Requests>>(emptyList())
    val requests: StateFlow<List<Requests>> = _requests

    private val _articles = MutableStateFlow<List<Articles>>(emptyList())
    val articles: StateFlow<List<Articles>> = _articles

    init {
        loadArticles()
        loadRequests()
    }

    fun refresh() {
        viewModelScope.launch {
            _isLoading.update { true }
            delay(2000)
            try {
                loadArticles()
                loadRequests()
            } finally {
                _isLoading.update { false }
            }
        }
    }

    private fun loadArticles() {
        viewModelScope.launch {
            val result = databaseManagerState.getArticles()
            _articles.value = result.ifEmpty {
                listOf(
                    Articles(
                        id = "1",
                        title = "Sample Article",
                        imageUrl = "https://example.com/image.jpg",
                        content = "This is a sample article content.",
                        createdAt = Instant.parse("2023-10-01T00:00:00Z")
                    )
                )
            }
        }
    }

    private fun loadRequests() {
        viewModelScope.launch {
            val result = databaseManagerState.getRequests()
            _requests.value = result.ifEmpty {
                listOf(
                    Requests(
                        id = "1",
                        userId = "impossible_user",
                        status = "Generated",
                        description = "Impossible request for placeholder",
                        imageUrl = "https://example.com/impossible.png",
                        createdAt = Instant.fromEpochMilliseconds(0)
                    )
                )
            }
        }
    }
}
