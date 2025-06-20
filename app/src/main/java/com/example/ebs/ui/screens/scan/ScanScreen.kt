package com.example.ebs.ui.screens.scan

import android.Manifest
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.components.structures.CenterRow
import com.example.ebs.ui.components.texts.TextContentM
import com.example.ebs.ui.components.texts.TextTitleS
import com.example.ebs.ui.dialogues.ReminderResult
import com.example.ebs.ui.screens.MainViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("ContextCastToActivity")
@Composable
fun ScanScreen(
    viewModelMain: MainViewModel,
    viewModel: ScanScreenViewModel = hiltViewModel()
){
    Log.d("Route", "This is Scan")

    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    if (cameraPermissionState.status.isGranted) {
        CameraPreviewContent(viewModel,viewModelMain)
    } else {
        CameraPermissionRequester(viewModelMain.navHandler){}
    }
    if(viewModelMain.authManagerState.checkVerification() == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable {
                    viewModelMain.navHandler.back()
                }
        ) {
            ReminderResult(
                onCancel = {
                    viewModelMain.navHandler.back()
                },
                onConfirm = {
                    viewModelMain.navHandler.back()
                },
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(0.95f)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                CenterRow(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp, top = 10.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (isSystemInDarkTheme())
                                MaterialTheme.colorScheme.background
                            else
                                MaterialTheme.colorScheme.surface
                        ).padding(10.dp)
                ) {
                    CenterColumn(
                        hAli = Alignment.Start,
                        vArr = Arrangement.Top,
                        modifier = Modifier
                            .align(Top)
                            .padding(start = 5.dp)
                    ) {
                        TextTitleS(
                            text = "Hmmm, sepertinya kamu belum terverifikasi",
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextContentM(
                            text = "Silakan verifikasi akunmu terlebih dahulu untuk menggunakan fitur ini",
                            modifier = Modifier.padding(bottom = 8.dp),
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }
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
