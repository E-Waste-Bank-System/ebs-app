package com.example.ebs

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.ebs.ui.navigation.MyNavigationg
import com.example.ebs.ui.theme.EBSTheme
import dagger.hilt.android.AndroidEntryPoint

private var navigateTo: String? = null
private var scanResult: String? = null

/*
 * MainActivity.kt
 * This file is part of the EBS (Electronic Book Scanner) application.
 * This application is designed to scan and manage electronic books.
 * It is developed using Kotlin and Jetpack Compose for the Android platform.
 * * The application utilizes Hilt for dependency injection and follows modern Android development practices.
 * * The MainActivity serves as the entry point of the application, setting up the initial UI and navigation.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        val userPreferencesRepository =
            (application as EbsApplication).userPreferencesRepository
        var navigateTo =
            intent.getStringExtra("navigate_to")
        var scanResult =
            intent.getStringExtra("scan_result")
        val resetIntentData:() -> Unit = {
                intent.putExtra("navigate_to", "")
                intent.putExtra("scan_result", "")
            }
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            EBSTheme {
                MyNavigationg(userPreferencesRepository, navigateTo, scanResult, resetIntentData)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        scanResult = intent.getStringExtra("scan_result")
        navigateTo = intent.getStringExtra("navigate_to")
    }
}

