package com.example.ebs.ui.dialogues

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.ebs.R
import com.example.ebs.service.UpdateService
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.components.structures.CenterRow
import com.example.ebs.ui.components.texts.TextContentM
import com.example.ebs.ui.components.texts.TextTitleM
import com.example.ebs.ui.components.texts.TextTitleS
import com.example.ebs.ui.dialogues.bases.ReminderResult
import com.example.ebs.ui.screens.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun UpdateAvailable(
    maxVer: String,
    trigger: MutableState<Boolean>,
    coroutine: CoroutineScope,
    updateService: UpdateService,
    viewModelMain: MainViewModel
){
    val wait = remember { mutableStateOf(false) }
    val progressor = remember { mutableFloatStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
    ) {
        ReminderResult(
            onCancel = {
                trigger.value = !trigger.value
                wait.value = !wait.value
                viewModelMain.navHandler.back()
            },
            onConfirm = {
                coroutine.launch {
                    updateService.downloadAndInstallUpdate(wait,maxVer,progressor,trigger)
                }
            },
            modifier = Modifier
                .align(Center)
                .fillMaxWidth(0.95f)
                .clip(RoundedCornerShape(8.dp)),
            updateMod = wait
        ) {
            if (wait.value) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.4f)
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
                        progress = { progressor.floatValue },
                        modifier = Modifier
                            .size(90.dp),
                        trackColor = ProgressIndicatorDefaults.circularIndeterminateTrackColor,
                    )
                    TextTitleM(
                       "Downloading the latest\nversion apk...",
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .align(BottomCenter)
                    )
                }
            } else {
                CenterRow(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp, top = 10.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (isSystemInDarkTheme())
                                MaterialTheme.colorScheme.background
                            else
                                MaterialTheme.colorScheme.surface
                        )
                        .padding(10.dp)
                ) {
                    CenterColumn(
                        hAli = Alignment.Start,
                        vArr = Arrangement.Top,
                        modifier = Modifier
                            .align(Alignment.Top)
                            .padding(start = 5.dp)
                    ) {
                        TextTitleS(
                            text = "Update Tersedia \uD83D\uDE80",
                            textAlign = TextAlign.Start
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextContentM(
                            text = "Versi terbaru aplikasi telah dirilis. " +
                                    "Kami sarankan untuk memperbarui agar mendapatkan fitur dan perbaikan terbaru.",
                            modifier = Modifier.padding(bottom = 8.dp),
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }
        }
    }
}