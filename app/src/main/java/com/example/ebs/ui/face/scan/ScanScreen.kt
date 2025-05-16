package com.example.ebs.ui.face.scan

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.compose.CameraXViewfinder
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.ebs.R
import com.example.ebs.ui.face.AuthViewModel
import com.example.ebs.ui.face.components.shapes.TopBarPage
import com.example.ebs.ui.face.components.structures.CenterRow
import com.example.ebs.ui.face.dialogue.ReqCam
import com.example.ebs.ui.navigation.NavigationHandler
import com.example.ebs.ui.navigation.destinations.Route
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File
@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("ContextCastToActivity")
@Composable
fun ScanScreen(
    navController: NavController,
    viewModel: ScanScreenViewModel = hiltViewModel(),
    viewModelAuth: AuthViewModel = hiltViewModel()
){
    viewModelAuth.initializeNavHandler(navController)
    Log.d("Route", "This is Scan")
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    if (cameraPermissionState.status.isGranted) {
        CameraPreviewContent(viewModel,viewModelAuth.navHandler)
    } else {
        CameraPermissionRequester(viewModelAuth.navHandler){}
//        CenterColumn(
//            modifier = modifier.fillMaxSize().wrapContentSize().widthIn(max = 480.dp)
//        ) {
//            val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
//                // If the user has denied the permission but the rationale can be shown,
//                // then gently explain why the app requires this permission
//                "Whoops! Looks like we need your camera to work our magic!" +
//                        "Don't worry, we just wanna see your pretty face (and maybe some cats).  " +
//                        "Grant us permission and let's get this party started!"
//            } else {
//                // If it's the first time the user lands on this feature, or the user
//                // doesn't want to be asked again for this permission, explain that the
//                // permission is required
//                "Hi there! We need your camera to work our magic! ✨\n" +
//                        "Grant us permission and let's get this party started! \uD83C\uDF89"
//            }
//            Text(textToShow, textAlign = TextAlign.Center)
//            Spacer(Modifier.height(16.dp))
//            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
//                Text("Unleash the Camera!")
//            }
//        }
    }
}

@Composable
private fun CameraPreviewContent(
    viewModel: ScanScreenViewModel,
    navHandler: NavigationHandler,
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val surfaceRequest by viewModel.surfaceRequest.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val imageCapture = remember { viewModel.imageCapture } // Add this to your ViewModel

    LaunchedEffect(lifecycleOwner, viewModel.cameraSelector.collectAsStateWithLifecycle().value) {
        viewModel.bindToCamera(context.applicationContext, lifecycleOwner)
    }

    surfaceRequest?.let { request ->
        Box {
            TopBarPage(buildAnnotatedString {
                withStyle(SpanStyle(color = Color.White)) {
                    append("Pindai")
                }
            },navHandler,
                noPad = true,
                customBack = Color.Black.copy(alpha = 0f),
                mod = true
            ) {
                Box {
                    CameraXViewfinder(
                        surfaceRequest = request,
                        modifier = modifier
                    )
                    Box(
                        modifier = Modifier
                            .align(TopCenter)
                            .fillMaxWidth()
                            .fillMaxHeight(0.3f)
                            .background(Color.Black.copy(alpha = 0.5f))
                    ) {}
                    Box(
                        modifier = Modifier
                            .align(BottomCenter)
                            .fillMaxWidth()
                            .fillMaxHeight(0.3f)
                            .background(Color.Black.copy(alpha = 0.5f))
                    ) {}
                    Box(
                        modifier = Modifier
                            .align(CenterEnd)
                            .fillMaxWidth(0.1f)
                            .fillMaxHeight(0.4f)
                            .background(Color.Black.copy(alpha = 0.5f))
                    ) {}
                    Box(
                        modifier = Modifier
                            .align(CenterStart)
                            .fillMaxWidth(0.1f)
                            .fillMaxHeight(0.4f)
                            .background(Color.Black.copy(alpha = 0.5f))
                    ) {}
                }
            }
            // Dock with capture button at the bottom
            CenterRow(
                modifier = Modifier
                    .align(BottomCenter)
                    .fillMaxWidth()
                    .padding(vertical = 50.dp)
            ) {
                CenterRow(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    IconButton(
                        onClick = { viewModel.toggleCamera() },
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.camera_switch_outline), // Add a suitable icon
                            contentDescription = "Switch Camera",
                            tint = Color.White
                        )
                    }
                }
                CenterRow(
                    modifier = Modifier
                        .weight(0.8f)
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
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = { /* TODO: Add capture logic */ },
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
                        Spacer(modifier = Modifier.weight(0.1f))
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
                            Image(
                                painterResource(R.drawable.nopicture),
                                contentDescription = "Album",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                                    .clickable {
                                        val photoFile = File(context.cacheDir, "captured_image_${System.currentTimeMillis()}.jpg")
                                        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
                                        imageCapture.takePicture(
                                            outputOptions,
                                            ContextCompat.getMainExecutor(context),
                                            object : ImageCapture.OnImageSavedCallback {
                                                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                                                    // Use the image URI: output.savedUri or photoFile
                                                    // Update your ViewModel/state here
                                                }
                                                override fun onError(exception: ImageCaptureException) {
                                                    // Handle error
                                                }
                                            }
                                        )
                                    }
                            )
                        }
                        Spacer(modifier = Modifier.weight(0.1f))
                    }
                }
            }
        }
    }
}

@Composable
fun CameraPermissionRequester(navHandler: NavigationHandler, onPermissionGranted: () -> Unit) {
    val context = LocalContext.current
    val cameraPermission = Manifest.permission.CAMERA
    val showDialog = remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
       if (isGranted) {
           onPermissionGranted()
       } else {
           Toast.makeText(context, "Camera permission denied. Unable to take photos.", Toast.LENGTH_SHORT).show()
       }
    }

    LaunchedEffect(key1 = true) {
        val permissionCheckResult = ContextCompat.checkSelfPermission(context, cameraPermission)
        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
            onPermissionGranted()
        } else {
            showDialog.value = true
        }
    }

    if (showDialog.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            ReqCam(
                rightAct = {
                    showDialog.value = false
                    permissionLauncher.launch(cameraPermission)
                },
                leftAct = {
                    showDialog.value = false
                    navHandler.back()
                },
            )
        }
    }
}

//fun ScanScreen(
//    navController: NavController,
//    signedIn: MutableState<String?>,
//    navHandler: NavigationHandler,
//    viewModel: ScanScreenViewModel =  hiltViewModel()
//) {
//    val currentContext = LocalContext.current
//    val tempFileUrl: Uri by remember { mutableStateOf(Uri.parse("content://placeholder") ) }
//
//    val pickImageFromAlbumLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(20)) { urls ->
//
//    }
//
//    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isImageSaved ->
//        if (isImageSaved) {
//
//        } else {
//
//        }
//    }
//
//    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { permissionGranted ->
//        if (permissionGranted) {
//
//        } else {
//            Toast.makeText(currentContext, "In order to take pictures, you have to allow this app to use your camera", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    LaunchedEffect(key1 = tempFileUrl) {
//        cameraLauncher.launch(tempFileUrl)
//    }
//
//    CameraPermissionRequester {  }
//
//    Button(onClick = {
//        permissionLauncher.launch(Manifest.permission.CAMERA)
//        cameraLauncher.launch(tempFileUrl)
//    }) {
//        Text(text = "Take a photo")
//    }
//    Spacer(modifier = Modifier.width(16.dp))
//    Button(onClick = {
//        pickImageFromAlbumLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//    }) {
//        Text(text = "Pick a picture")
//    }
//}
