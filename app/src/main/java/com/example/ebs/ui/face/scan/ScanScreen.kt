package com.example.ebs.ui.face.scan

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.compose.CameraXViewfinder
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.ebs.ui.face.components.structures.CenterColumn
import com.example.ebs.ui.navigation.NavigationHandler
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("ContextCastToActivity")
@Composable
fun ScanScreen(
    navController: NavController,
    signedIn: MutableState<String?>,
    navHandler: NavigationHandler,
    modifier: Modifier = Modifier,
    viewModel: ScanScreenViewModel = hiltViewModel()
){
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    if (cameraPermissionState.status.isGranted) {
        CameraPreviewContent(viewModel,modifier)
    } else {
        CenterColumn(
            modifier = modifier.fillMaxSize().wrapContentSize().widthIn(max = 480.dp)
        ) {
            val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
                // If the user has denied the permission but the rationale can be shown,
                // then gently explain why the app requires this permission
                "Whoops! Looks like we need your camera to work our magic!" +
                        "Don't worry, we just wanna see your pretty face (and maybe some cats).  " +
                        "Grant us permission and let's get this party started!"
            } else {
                // If it's the first time the user lands on this feature, or the user
                // doesn't want to be asked again for this permission, explain that the
                // permission is required
                "Hi there! We need your camera to work our magic! ✨\n" +
                        "Grant us permission and let's get this party started! \uD83C\uDF89"
            }
            Text(textToShow, textAlign = TextAlign.Center)
            Spacer(Modifier.height(16.dp))
            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                Text("Unleash the Camera!")
            }
        }
    }
}

@Composable
private fun CameraPreviewContent(
    viewModel: ScanScreenViewModel,
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val surfaceRequest by viewModel.surfaceRequest.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(lifecycleOwner) {
        viewModel.bindToCamera(context.applicationContext, lifecycleOwner)
    }

    surfaceRequest?.let { request ->
        CameraXViewfinder(
            surfaceRequest = request,
            modifier = modifier
        )
    }
}

@Composable
fun CameraPermissionRequester(onPermissionGranted: () -> Unit) {
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
            if ((context as ComponentActivity).shouldShowRequestPermissionRationale(cameraPermission)) {
                showDialog.value = true
            } else {
                permissionLauncher.launch(cameraPermission)
            }
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Butuh Akses Kamera") },
            text = { Text("Tolong izinkan akses kamera untuk menggunakan fungsi ini :)") },
            confirmButton = {
                Button(
                    onClick = {
                    showDialog.value = false
                    permissionLauncher.launch(cameraPermission)
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog.value = false }) {
                    Text("Cancel")
                }
            }
        )
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
