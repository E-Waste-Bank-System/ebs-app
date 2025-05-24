package com.example.ebs.ui.screens.scan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BoxShade() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        //atas
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .background(Color.Black.copy(alpha = 0.5f))
        )
        //bawah
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.29999f)
                .background(Color.Black.copy(alpha = 0.5f))
        )
        //kanan
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxWidth(0.1f)
                .fillMaxHeight(0.4f)
                .background(Color.Black.copy(alpha = 0.5f))
        )
        //kiri
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .fillMaxWidth(0.1f)
                .fillMaxHeight(0.4f)
                .background(Color.Black.copy(alpha = 0.5f))
        )
    }
}