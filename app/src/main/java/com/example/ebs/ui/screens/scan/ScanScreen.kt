package com.example.ebs.ui.screens.scan

import android.Manifest
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ebs.ui.screens.MainViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("ContextCastToActivity")
@Composable
fun ScanScreen(
    navController: NavController,
    viewModelAuth: MainViewModel,
    viewModel: ScanScreenViewModel = hiltViewModel()
){
    Log.d("Route", "This is Scan")

    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    if (cameraPermissionState.status.isGranted) {
        CameraPreviewContent(viewModel,viewModelAuth)
    } else {
        CameraPermissionRequester(viewModelAuth.navHandler){}       
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
