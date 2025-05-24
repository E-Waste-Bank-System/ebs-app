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

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        val navigateTo = intent.getStringExtra("navigate_to")
        setContent {
            EBSTheme {
                val userPreferencesRepository = (application as EbsApplication).userPreferencesRepository
                MyNavigationg(userPreferencesRepository, navigateTo)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        navigateTo = intent.getStringExtra("navigate_to")
    }
}

