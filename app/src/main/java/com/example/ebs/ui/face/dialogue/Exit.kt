package com.example.ebs.ui.face.dialogue

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ebs.ui.navigation.NavigationHandler

@Composable
fun Exit(
    navHandler: NavigationHandler,
    modifier: Modifier = Modifier
) {
    CustomAlertDialogue(
        title = "Keluar Aplikasi",
        desc = "Apakah anda yakin ingin keluar dari aplikasi?",
        right = "Ya",
        left = "Tidak",
        modifier,
        rightAct = { navHandler.closeApp() },
        leftAct = { navHandler.back() }
    )
}
