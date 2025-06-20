package com.example.ebs.ui.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ebs.R
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.components.structures.CenterRow
import com.example.ebs.ui.components.texts.TextContentM
import com.example.ebs.ui.components.texts.TextTitleS
import com.example.ebs.ui.screens.MainViewModel

@Composable
fun ReminderScanResult(
    viewModelMain: MainViewModel,
    reminder: MutableState<Boolean>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable {
                reminder.value = !reminder.value
            }
    ) {
        com.example.ebs.ui.dialogues.ReminderResult(
            onCancel = {
                viewModelMain.navHandler.back()
            },
            onConfirm = {
                reminder.value = !reminder.value
            },
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.95f)
                .clip(RoundedCornerShape(8.dp))
        ) {
            CenterRow(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        if (isSystemInDarkTheme())
                            MaterialTheme.colorScheme.background
                        else
                            MaterialTheme.colorScheme.surface
                    ).padding(10.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.star),
                    contentDescription = "star",
                    modifier = Modifier
                        .padding(5.dp)
                        .size(30.dp)
                        .align(Alignment.Top),
                    contentScale = ContentScale.Crop
                )
                CenterColumn(
                    hAli = Alignment.Start,
                    vArr = Arrangement.Top,
                    modifier = Modifier
                        .align(Alignment.Top)
                ) {
                    TextTitleS(text = "Catatan:")
                    TextContentM(
                        text = "Estimasi harga dan saran berbasis AI dapat berubah tergantung kondisi fisik perangkat dan modelnya. Untuk hasil maksimal, simpan perangkat dalam keadaan utuh saat disetor.",
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
    }
}