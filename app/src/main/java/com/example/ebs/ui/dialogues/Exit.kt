package com.example.ebs.ui.dialogues

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ebs.ui.screens.MainViewModel

@Composable
fun Exit(
    viewModelMain: MainViewModel,
    modifier: Modifier = Modifier
) {
    CustomAlertDialogue(
        title = "Keluar Aplikasi",
        desc = "Apakah anda yakin ingin keluar dari aplikasi?",
        right = "Ya",
        left = "Tidak",
        modifier,
        rightAct = { viewModelMain.navHandler.closeApp() },
        leftAct = { viewModelMain.navHandler.back() }
    )
}
