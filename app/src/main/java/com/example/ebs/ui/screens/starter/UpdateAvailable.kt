package com.example.ebs.ui.screens.starter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ebs.service.UpdateService
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.components.structures.CenterRow
import com.example.ebs.ui.components.texts.TextContentM
import com.example.ebs.ui.components.texts.TextTitleS
import com.example.ebs.ui.dialogues.ReminderResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun UpdateAvailable(
    trigger: MutableState<Boolean>,
    coroutine: CoroutineScope,
    updateService: UpdateService
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable {
                trigger.value = !trigger.value
            }
    ) {
        ReminderResult(
            onCancel = {
                trigger.value = !trigger.value
            },
            onConfirm = {
                coroutine.launch {
                    updateService.downloadAndInstallUpdate()
                }
                trigger.value = !trigger.value
            },
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.95f)
                .clip(RoundedCornerShape(8.dp))
        ) {
            CenterRow(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp, top = 10.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        if (isSystemInDarkTheme())
                            MaterialTheme.colorScheme.background
                        else
                            MaterialTheme.colorScheme.surface
                    ).padding(10.dp)
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