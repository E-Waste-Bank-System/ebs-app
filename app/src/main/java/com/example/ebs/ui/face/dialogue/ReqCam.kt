package com.example.ebs.ui.face.dialogue

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ebs.ui.navigation.NavigationHandler

@Composable
fun ReqCam(
    rightAct: () -> Unit,
    leftAct: () -> Unit,
    modifier: Modifier = Modifier
) {
    CustomAlertDialogue(
        title = "Butuh Akses Kamera",
        desc = "Tolong izinkan akses kamera untuk menggunakan fungsi ini :)",
        right = "Okey",
        left = "Cancel",
        modifier,
        rightAct = rightAct,
        leftAct = leftAct
    )
}