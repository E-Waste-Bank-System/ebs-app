package com.example.ebs.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.ebs.R
import com.example.ebs.ui.components.structures.CenterRow
import com.example.ebs.ui.components.texts.TextTitleL
import com.example.ebs.ui.components.texts.TextTitleXL
import com.example.ebs.ui.screens.MainViewModel

@Composable
fun Greeting(viewModelMain: MainViewModel) {
    CenterRow(
        hArr = Arrangement.SpaceBetween,
        vAli = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 30.dp)
    ) {
        Column {
            TextTitleXL("Hallo, ${
                try {
                    viewModelMain.localInfo.name?.split(" ")?.firstOrNull() ?:""
                } catch (e: UninitializedPropertyAccessException) {
                    viewModelMain.firstOpen = true
                    viewModelMain.navHandler.dashboard()
                }
            }")
            TextTitleL(
                buildAnnotatedString {
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(stringResource(R.string.ayo))
                    }
                    append("\n")
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(stringResource(R.string.elek))
                    }
                },
                textAlign = TextAlign.Start
            )
        }

        CenterRow(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background)
                .clickable { viewModelMain.navHandler.notifikasiFromMenu() }
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painterResource(R.drawable.bell),
                    contentDescription = "Notification",
                    tint = MaterialTheme.colorScheme.onSurface,
                )
                if (true) {
                    CenterRow(
                        modifier = Modifier
                            .absoluteOffset(x = 5.dp, y = 4.dp)
                            .padding(bottom = 20.dp)
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(
                                Color.Red.copy(
                                    red = 0.7f
                                )
                            )
                    ) {}
                }
            }
        }
    }
}