package com.example.ebs.ui.screens.scan

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.ebs.ui.dialogues.ReqCam
import com.example.ebs.ui.navigation.NavigationHandler

@Composable
fun CameraPermissionRequester(
    navHandler: NavigationHandler,
    onPermissionGranted: () -> Unit
) {
    val context = LocalContext.current
    val cameraPermission = Manifest.permission.CAMERA
    val showDialog = remember { mutableStateOf(false) }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                onPermissionGranted()
            } else {
                Toast.makeText(
                    context,
                    "Camera permission denied. Unable to take photos.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    LaunchedEffect(key1 = true) {
        val permissionCheckResult =
            ContextCompat.checkSelfPermission(
                context, cameraPermission
            )
        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
            onPermissionGranted()
        } else {
            showDialog.value = true
        }
    }

    if (showDialog.value) {
        Box(
            modifier = Modifier.Companion
                .fillMaxSize()
                .background(Color.Companion.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Companion.Center
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