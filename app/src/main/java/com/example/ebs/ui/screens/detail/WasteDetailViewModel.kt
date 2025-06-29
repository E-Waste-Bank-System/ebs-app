package com.example.ebs.ui.screens.detail

import androidx.lifecycle.ViewModel
import com.example.ebs.data.structure.remote.book.DataTest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class WasteDetailViewModel @Inject constructor(
) : ViewModel() {
//    private val _detectionListUiState = MutableStateFlow<WasteDetailUiState>(WasteDetailUiState.Loading)
//    val detectionListUiState: StateFlow<WasteDetailUiState> = _detectionListUiState
//
//    init {
////        getData()
//    }
//
//    fun getData() {
//        viewModelScope.launch {
//            _detectionListUiState.value = WasteDetailUiState.Loading
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
//                        _detectionListUiState.value = WasteDetailUiState.Success(data)
//                    }
//            } catch (e: IOException) {
//                _detectionListUiState.value = WasteDetailUiState.Error(e)
//            } catch (e: HttpException) {
//                _detectionListUiState.value = WasteDetailUiState.Error(e)
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


sealed interface WasteDetailUiState {
    data class Success(val dataTestList: DataTest) : WasteDetailUiState
    data class Error(val e: Exception) : WasteDetailUiState
    object Loading : WasteDetailUiState
}