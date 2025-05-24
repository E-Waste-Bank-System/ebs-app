package com.example.ebs.ui.dialogues

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.ebs.ui.screens.MainViewModel

@Composable
fun Exit(
    navController: NavController,
    viewModelAuth: MainViewModel,
    modifier: Modifier = Modifier
) {
    CustomAlertDialogue(
        title = "Keluar Aplikasi",
        desc = "Apakah anda yakin ingin keluar dari aplikasi?",
        right = "Ya",
        left = "Tidak",
        modifier,
        rightAct = { viewModelAuth.navHandler.closeApp() },
        leftAct = { viewModelAuth.navHandler.back() }
    )
}
