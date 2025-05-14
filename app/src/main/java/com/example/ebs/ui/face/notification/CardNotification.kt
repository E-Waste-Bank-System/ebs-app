package com.example.ebs.ui.face.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
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
import androidx.compose.ui.unit.dp
import com.example.ebs.R
import com.example.ebs.ui.face.components.structures.CenterRow

@Composable
fun CardNotification(
    modifier: Modifier = Modifier.Companion,
    icon: Int = R.drawable.recycle,
    content: @Composable () -> Unit
){
    CenterRow(
        vAli = Alignment.Companion.Top,
        modifier = modifier
            .padding(vertical = 10.dp)
    ) {
        Box(
            contentAlignment = Alignment.Companion.Center
        ) {
            CenterRow(
                modifier = Modifier.Companion
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    painterResource(icon),
                    contentDescription = "Notification",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            if (true) {
                CenterRow(
                    modifier = Modifier.Companion
                        .absoluteOffset(x = 13.dp, y = 25.dp)
                        .padding(bottom = 20.dp)
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(
                            Color.Companion.Green.copy(
                                green = 0.7f
                            )
                        )
                ) {}
            }
        }
        content()
    }
}