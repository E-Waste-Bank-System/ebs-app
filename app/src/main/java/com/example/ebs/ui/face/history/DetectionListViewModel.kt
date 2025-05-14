package com.example.ebs.ui.face.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ebs.data.repositories.remote.Book
import com.example.ebs.data.repositories.remote.BookData
import com.example.ebs.data.repositories.remote.DataTest
import com.example.ebs.data.repositories.remote.DataTestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.chrisbanes.haze.HazeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetectionListViewModel @Inject constructor(
    private val dataTestRepository: DataTestRepository
) : ViewModel() {
    val hazeState = HazeState()
//    private val _detectionListUiState = MutableStateFlow<DetectionListUiState>(DetectionListUiState.Loading)
//    val detectionListUiState: StateFlow<DetectionListUiState> = _detectionListUiState

    init {
//        getData()
    }
//
//    fun getData() {
//        viewModelScope.launch {
//            _detectionListUiState.value = DetectionListUiState.Loading
//            try {
//                val dataFlow = dataTestRepository.getData()
//                    .stateIn(
//                        scope = viewModelScope,
//                        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
//                        initialValue = DataTest(
//                            status = "---",
//                            data = BookData(
//                                book = Book(
//                                    id = "",
//                                    name = "",
//                                    year = 0,
//                                    author = "",
//                                    summary = "",
//                                    publisher = "",
//                                    pageCount = 0,
//                                    readPage = 0,
//                                    finished = false,
//                                    reading = false,
//                                    insertedAt = "",
//                                    updatedAt = ""
//                                )
//                            )
//                        )
//                    ).collect { data ->
//                        _detectionListUiState.value = DetectionListUiState.Success(data)
//                    }
//            } catch (e: IOException) {
//                _detectionListUiState.value = DetectionListUiState.Error(e)
//            } catch (e: HttpException) {
//                _detectionListUiState.value = DetectionListUiState.Error(e)
//            }
//        }
//    }
//
//    companion object {
//        private const val TIMEOUT_MILLIS = 5_000L
//    }

//    /**
//     * Factory for [MarsViewModel] that takes [MarsPhotosRepository] as a dependency
//     */
//    companion object {
//        val Factory: ViewModelProvider.Factory = viewModelFactory {
//            initializer {
//                val application = (this[APPLICATION_KEY] as MarsPhotosApplication)
//                val marsPhotosRepository = application.container.marsPhotosRepository
//                MarsViewModel(marsPhotosRepository = marsPhotosRepository)
//            }
//        }
//    }
}

sealed interface DetectionListUiState {
    data class Success(val dataTestList: DataTest) : DetectionListUiState
    data class Error(val e: Exception) : DetectionListUiState
    object Loading : DetectionListUiState
}