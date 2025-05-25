package com.example.ebs.ui.screens.scan

import android.media.MediaScannerConnection
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.compose.CameraXViewfinder
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.ebs.R
import com.example.ebs.data.structure.remote.ebs.detections.DataDetections
import com.example.ebs.ui.components.shapes.TopBarPage
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.components.structures.CenterRow
import com.example.ebs.ui.components.texts.TextContentM
import com.example.ebs.ui.components.texts.TextTitleS
import com.example.ebs.ui.dialogues.ReminderResult
import com.example.ebs.ui.screens.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@Composable
internal fun CameraPreviewContent(
    viewModel: ScanScreenViewModel,
    viewModelAuth: MainViewModel,
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE

    val upImage by viewModelAuth.upImage.collectAsState()
    val surfaceRequest by viewModel.surfaceRequest.collectAsStateWithLifecycle()
    val imageCapture = remember { viewModel.imageCapture }

    val scope = rememberCoroutineScope()
    val wait = remember { mutableStateOf(false) }

    val userInfo = try {
        viewModelAuth.localInfo
    } catch (e: UninitializedPropertyAccessException) {
        viewModelAuth.firstOpen = true
        viewModelAuth.navHandler.dashboard()
        return
    }

    val reminder = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(lifecycleOwner, viewModel.cameraSelector.collectAsStateWithLifecycle().value) {
        viewModel.bindToCamera(context.applicationContext, lifecycleOwner)
    }

    surfaceRequest?.let { request ->
        val pickImageFromAlbumLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.PickVisualMedia()) { url ->
            if (url != null) {
                scope.launch {
                    try {
                        wait.value = true
                        val tempFile = withContext(Dispatchers.IO) {
                            val inputStream = context.contentResolver.openInputStream(url)
                            val temp = File(context.cacheDir, "upload_${System.currentTimeMillis()}.jpg")
                            inputStream?.use { input ->
                                temp.outputStream().use { output ->
                                    input.copyTo(output)
                                }
                            }
                            temp
                        }
                        withContext(Dispatchers.IO) {
                            viewModelAuth.uploadImage(tempFile.absolutePath)
                        }
                        val result = upImage
                        if (result != DataDetections()) {
                            viewModelAuth.navHandler.detailFromMenu(result)
                        } else {
                            Toast.makeText(context, "Failed to upload image", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.e("CameraPreviewContent", "Error uploading image: ${e.localizedMessage}")
                        Toast.makeText(context, "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                    } finally {
                        wait.value = false
                    }
                }
            }
        }
        Box {
            TopBarPage(buildAnnotatedString {
                withStyle(SpanStyle(color = Color.White)) {
                    append("Pindai")
                }
            },viewModelAuth.navHandler,
                noPad = true,
                customBack = Color.Black.copy(alpha = 0f),
                mod = true
            ) {
                Box {
                    CameraXViewfinder(
                        surfaceRequest = request,
                        modifier = modifier
                            .align(Alignment.Center)
                            .fillMaxSize()
                            .aspectRatio(
                                LocalWindowInfo.current.containerSize.let {
                                    if (it.height != 0) it.width.toFloat() / it.height.toFloat() else 1f
                                }
                            )
                    )
                    BoxShade()
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            ) {
                if (wait.value) {
                    CenterColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.5f))
                    ) {
                        AnimatedVisibility(
                            visible = wait.value,
                            enter = fadeIn(),
//slideOutHorizontally()
//scaleOut() + slideInVertically(initialOffsetY = { it }) + slideOutVertically()
//shrinkOut(),
                            exit = fadeOut(tween(durationMillis = 1000, delayMillis = 0))
                        ) {
                            LottieAnimation(
                                composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.aonm)).value,
                                iterations = LottieConstants.IterateForever,
                                modifier = Modifier
                                    .size(250.dp)
                            )
                        }
//                        CircularProgressIndicator()
//                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
//                        CircularProgressIndicator(
//                            color = MaterialTheme.colorScheme.primary,
//                            strokeWidth = 6.dp,
//                            modifier = Modifier.size(48.dp)
//                        )
                    }
                }
            }
            CenterRow(
                modifier = Modifier
                    .align(Alignment.Companion.BottomCenter)
                    .fillMaxWidth()
                    .padding(vertical = 50.dp)
            ) {
                CenterRow(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    CenterRow(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .background(
                                color = Color.Black.copy(alpha = 0.5f), // Opaque white with slight transparency
                                shape = CircleShape
                            )
                    ) {
                        Spacer(modifier = Modifier.weight(0.2f))
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(vertical = 10.dp)
                                .clickable {},
                            contentAlignment = Alignment.Center
                        ) {
                            IconButton(
                                onClick = { viewModel.toggleCamera() }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.camera_switch_outline), // Add a suitable icon
                                    contentDescription = "Switch Camera",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(75.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.weight(0.2f))
                    }
                }
                CenterRow(
                    modifier = Modifier
                        .weight(0.8f)
                        .background(
                            color = Color.Black.copy(alpha = 0.5f),
                            shape = CircleShape
                        )
                ) {
                    Spacer(modifier = Modifier.weight(0.2f))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(vertical = 10.dp)
                            .background(
                                color = Color.White,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = {
                                if(userInfo.emailVerified == "true") {
//                              val picturesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//                              val photoFile = File(picturesDir, "captured_image_${System.currentTimeMillis()}.jpg")
                                    val photoFile = File(
                                        context.cacheDir,
                                        "captured_image_${System.currentTimeMillis()}.png"
                                    )
                                    val outputOptions =
                                        ImageCapture.OutputFileOptions.Builder(photoFile).build()
                                    imageCapture.takePicture(
                                        outputOptions,
                                        ContextCompat.getMainExecutor(context),
                                        object : ImageCapture.OnImageSavedCallback {
                                            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                                                scope.launch {
                                                    try {
                                                        wait.value = true
                                                        // Save to gallery
//                                                    val picturesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//                                                    val picturesDir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Electronic Waste Hub")
                                                        val picturesDir = File(
                                                            Environment.getExternalStoragePublicDirectory(
                                                                Environment.DIRECTORY_PICTURES
                                                            ), "Electronic Waste Hub"
                                                        )
                                                        if (!picturesDir.exists()) picturesDir.mkdirs()
                                                        val galleryFile = File(
                                                            picturesDir,
                                                            "captured_image_${System.currentTimeMillis()}.png"
                                                        )
                                                        photoFile.copyTo(
                                                            galleryFile,
                                                            overwrite = true
                                                        )
                                                        // Scan file so it appears in gallery
                                                        MediaScannerConnection.scanFile(
                                                            context,
                                                            arrayOf(galleryFile.absolutePath),
                                                            arrayOf("image/png"),
                                                            null
                                                        )
                                                        withContext(Dispatchers.IO) {
                                                            viewModelAuth.uploadImage(photoFile.toString())
                                                        }
                                                        val result = viewModelAuth.upImage.value
                                                        if (result != DataDetections()) {
                                                            viewModelAuth.resetUpImage()
                                                            viewModelAuth.navHandler.detailFromMenu(
                                                                result
                                                            )
                                                        } else {
                                                            Toast.makeText(
                                                                context,
                                                                "Failed to upload image",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }
                                                    } catch (e: Exception) {
                                                        Log.e(
                                                            "CameraPreviewContent",
                                                            "Error uploading image: ${e.localizedMessage}"
                                                        )
                                                        Toast.makeText(
                                                            context,
                                                            "Error: ${e.localizedMessage}",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    } finally {
                                                        wait.value = false
                                                    }
                                                }
                                            }

                                            override fun onError(e: ImageCaptureException) {
                                                Toast.makeText(
                                                    context,
                                                    "Error: ${e.localizedMessage}",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    )
                                }
                            },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxSize()
                        ) {}
                    }
                    Spacer(modifier = Modifier.weight(0.2f))
                }
                CenterRow(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    CenterRow(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .background(
                                color = Color.Black.copy(alpha = 0.5f), // Opaque white with slight transparency
                                shape = CircleShape
                            )
                    ) {
                        Spacer(modifier = Modifier.weight(0.2f))
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(vertical = 10.dp)
                                .background(
                                    color = Color.White,
                                    shape = CircleShape
                                )
                                .clickable {
                                    pickImageFromAlbumLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painterResource(R.drawable.nopicture),
                                contentDescription = "Album",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                            )
                        }
                        Spacer(modifier = Modifier.weight(0.2f))
                    }
                }
            }
        }
        if(userInfo.emailVerified == "false") {
            if (!reminder.value){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .clickable {
                            reminder.value = !reminder.value
                        }
                ) {
                    ReminderResult(
                        onCancel = {
                            viewModelAuth.navHandler.back()
                        },
                        onConfirm = {
                            reminder.value = !reminder.value
                        },
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth(0.95f)
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        CenterRow(
                            modifier = Modifier
                                .padding(10.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSystemInDarkTheme()) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.surface)
                                .padding(10.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.star),
                                contentDescription = "star",
                                modifier = Modifier
                                    .padding(5.dp)
                                    .size(30.dp)
                                    .align(Top),
                                contentScale = ContentScale.Crop
                            )
                            CenterColumn(
                                hAli = Alignment.Start,
                                vArr = Arrangement.Top,
                                modifier = Modifier
                                    .align(Top)
                            ) {
                                TextTitleS(text = "PERHATIAN!")
                                TextContentM(
                                    text = "Email anda belum diverifikasi, silahkan verifikasi email anda untuk mendapatkan akses penuh.",
                                    modifier = Modifier.padding(bottom = 8.dp),
                                    textAlign = TextAlign.Start
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

