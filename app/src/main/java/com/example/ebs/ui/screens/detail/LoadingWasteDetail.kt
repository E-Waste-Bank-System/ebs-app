package com.example.ebs.ui.screens.detail

import android.content.Context
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.ebs.R
import com.example.ebs.data.structure.remote.ebs.detections.head.Detection
import com.example.ebs.data.structure.remote.ebs.detections.head.ScanResponse
import com.example.ebs.ui.components.inputs.AestheticButton
import com.example.ebs.ui.components.texts.TextTitleS
import com.example.ebs.ui.screens.MainViewModel

@Composable
fun LoadingWasteDetail(
    check: Boolean,
    progressor: Float,
    finishLine: Long,
    detection: Detection,
    scanRes: ScanResponse,
    context: Context,
    viewModelMain: MainViewModel,
    fromScan: Boolean = false
){
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Center
    ) {
        AnimatedVisibility(
            visible = !check,
            exit = fadeOut(tween(durationMillis = 1000, delayMillis = 0))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Center
            ) {
                LottieAnimation(
                    composition =
                        rememberLottieComposition(
                            LottieCompositionSpec.RawRes(R.raw.aimne)
                        ).value,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier
                        .size(250.dp),
                )
                CircularProgressIndicator(
                    progress = { progressor / finishLine },
                    modifier = Modifier
                        .size(90.dp),
                    trackColor =
                        ProgressIndicatorDefaults.circularIndeterminateTrackColor,
                )
                if (detection.status != "completed" && fromScan) {
                    AestheticButton(
                        onClick = {
                            val intent =
                                Intent(context, PollingForegroundService::class.java).apply {
                                    putExtra("scanId", scanRes.id)
                                    putExtra("status", detection.status)
                                }
                            ContextCompat.startForegroundService(context, intent)
                            viewModelMain.navHandler.back()
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Center)
                            .offset(x = 0.dp, y = 250.dp)
                    ) {
                        TextTitleS(
                            buildAnnotatedString {
                                withStyle(SpanStyle(color = Color.White)) {
                                    append("Lihat Menu Dulu")
                                }
                            },
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Center)
                        )
                    }
                }
            }
        }
    }
}