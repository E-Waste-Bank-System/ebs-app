package com.example.ebs.ui.face.dialogue

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ebs.ui.face.AuthViewModel
import com.example.ebs.ui.navigation.NavigationHandler

@Composable
fun Exit(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModelAuth: AuthViewModel = hiltViewModel()
) {
    viewModelAuth.initializeNavHandler(navController)
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
