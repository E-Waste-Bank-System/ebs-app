package com.example.ebs.ui.screens.scan

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class ScanScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val coroutineContext: CoroutineContext
) : ViewModel() {
    private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)
    val surfaceRequest: StateFlow<SurfaceRequest?> = _surfaceRequest
    private val _cameraSelector = MutableStateFlow(CameraSelector.DEFAULT_BACK_CAMERA)
    val cameraSelector: StateFlow<CameraSelector> = _cameraSelector

    private val cameraPreviewUseCase = Preview.Builder().build().apply {
        setSurfaceProvider { newSurfaceRequest ->
            _surfaceRequest.update { newSurfaceRequest }
        }
    }

    private val imageCaptureUseCase = ImageCapture.Builder().build()
    val imageCapture: ImageCapture
        get() = imageCaptureUseCase

    fun toggleCamera() {
        _cameraSelector.value = if (_cameraSelector.value == CameraSelector.DEFAULT_BACK_CAMERA)
            CameraSelector.DEFAULT_FRONT_CAMERA else CameraSelector.DEFAULT_BACK_CAMERA
    }

    suspend fun bindToCamera(appContext: Context, lifecycleOwner: LifecycleOwner) {
        val processCameraProvider = ProcessCameraProvider.awaitInstance(appContext)
        processCameraProvider.bindToLifecycle(
            lifecycleOwner, _cameraSelector.value, cameraPreviewUseCase, imageCaptureUseCase
        )

        // Cancellation signals we're done with the camera
        try { awaitCancellation() } finally { processCameraProvider.unbindAll() }
    }

//    //region View State
//    private val _albumViewState: MutableStateFlow<AlbumViewState> = MutableStateFlow(
//        AlbumViewState()
//    )
//    val viewStateFlow: StateFlow<AlbumViewState>
//        get() = _albumViewState
//    //endregion
//
//    // region Intents
//    fun onReceive(intent: Intent) = viewModelScope.launch(coroutineContext) {
//        when(intent) {
//            is Intent.OnPermissionGrantedWith -> {
//                // Create an empty image file in the app's cache directory
//                val tempFile = File.createTempFile(
//                    "temp_image_file_", /* prefix */
//                    ".jpg", /* suffix */
//                    intent.compositionContext.cacheDir  /* cache directory */
//                )
//
//                // Create sandboxed url for this temp file - needed for the camera API
//                val uri = FileProvider.getUriForFile(intent.compositionContext,
//                    "${BuildConfig.APPLICATION_ID}.provider", /* needs to match the provider information in the manifest */
//                    tempFile
//                )
//                _albumViewState.value = _albumViewState.value.copy(tempFileUrl = uri)
//            }
//
//            is Intent.OnPermissionDenied -> {
//                // maybe log the permission denial event
//                println("User did not grant permission to use the camera")
//            }
//
//            is Intent.OnFinishPickingImagesWith -> {
//                if (intent.imageUrls.isNotEmpty()) {
//                    // Handle picked images
//                    val newImages = mutableListOf<ImageBitmap>()
//                    for (eachImageUrl in intent.imageUrls) {
//                        val inputStream = intent.compositionContext.contentResolver.openInputStream(eachImageUrl)
//                        val bytes = inputStream?.readBytes()
//                        inputStream?.close()
//
//                        if (bytes != null) {
//                            val bitmapOptions = BitmapFactory.Options()
//                            bitmapOptions.inMutable = true
//                            val bitmap: Bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size, bitmapOptions)
//                            newImages.add(bitmap.asImageBitmap())
//                        } else {
//                            // error reading the bytes from the image url
//                            println("The image that was picked could not be read from the device at this url: $eachImageUrl")
//                        }
//                    }
//
//                    val currentViewState = _albumViewState.value
//                    val newCopy = currentViewState.copy(
//                        selectedPictures = (currentViewState.selectedPictures + newImages),
//                        tempFileUrl = null
//                    )
//                    _albumViewState.value = newCopy
//                } else {
//                    // user did not pick anything
//                }
//            }
//
//            is Intent.OnImageSavedWith -> {
//                val tempImageUrl = _albumViewState.value.tempFileUrl
//                if (tempImageUrl != null) {
//                    val source = ImageDecoder.createSource(intent.compositionContext.contentResolver, tempImageUrl)
//
//                    val currentPictures = _albumViewState.value.selectedPictures.toMutableList()
//                    currentPictures.add(ImageDecoder.decodeBitmap(source).asImageBitmap())
//
//                    _albumViewState.value = _albumViewState.value.copy(tempFileUrl = null,
//                        selectedPictures = currentPictures)
//                }
//            }
//
//            is Intent.OnImageSavingCanceled -> {
//                _albumViewState.value = _albumViewState.value.copy(tempFileUrl = null)
//            }
//
//            is Intent.OnPermissionGranted -> {
//                // unnecessary in this viewmodel variant
//            }
//
//            is Intent.OnFinishPickingImages -> {
//                // unnecessary in this viewmodel variant
//            }
//
//            is Intent.OnImageSaved -> {
//                // unnecessary in this viewmodel variant
//            }
//        }
//    }
    // endregion
}