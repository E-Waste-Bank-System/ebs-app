package com.example.ebs.ui.screens.dashboard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ebs.data.repositories.remote.ebsApi.EBSRepository
import com.example.ebs.data.structure.remote.ebs.articles.Articles
import com.example.ebs.data.structure.remote.ebs.articles.DataArticles
import com.example.ebs.service.auth.AuthManager
import com.example.ebs.service.database.DatabaseManager
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
    private val ebsRepository: EBSRepository,
    private val authManager: AuthManager,
    private val databaseManager: DatabaseManager
) : ViewModel() {
    val hazeState = HazeState()
    private val ebsRepositoryState: EBSRepository
        get() = ebsRepository
    val authManagerState: AuthManager
        get() = authManager
    private val databaseManagerState: DatabaseManager
        get() = databaseManager

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _articles2 = MutableStateFlow<DataArticles>(DataArticles(emptyList(),0))
    val articles2: StateFlow<DataArticles> = _articles2

    private val _articles = MutableStateFlow<List<Articles>>(emptyList())
    val articles: StateFlow<List<Articles>> = _articles

    init {
        loadArticles()
        loadArticles2()
    }

    fun refresh() {
        viewModelScope.launch {
            _isLoading.update { true }
            delay(2000)
            try {
                loadArticles()
                loadArticles2()
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

    private fun loadArticles2() {
        viewModelScope.launch {
            val token = authManagerState.getAuthToken()
            val result = ebsRepository.getData(token = token ?: "")
            _articles2.value = if (result.data.isEmpty()) {
                DataArticles(
                    listOf(
                        Articles(
                            id = "1",
                            title = "Sample Article",
                            imageUrl = "https://example.com/image.jpg",
                            content = "This is a sample article content.",
                            createdAt = Instant.parse("2023-10-01T00:00:00Z")
                        )
                    ),0
                )
            } else {
                result
            }
        }
    }
}
