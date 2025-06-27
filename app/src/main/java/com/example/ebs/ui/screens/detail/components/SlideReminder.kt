package com.example.ebs.ui.screens.detail.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.ebs.R
import com.example.ebs.data.structure.remote.ebs.detections.Waste

@Composable
fun SlideReminder(
    listScan: List<Waste>,
    slideReminder: MutableState<Boolean>,
    scrollToLastItem: MutableState<Boolean>
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                val lastIndex = listScan.size - 1
                if (lastIndex >= 0) {
                    slideReminder.value = false
                    scrollToLastItem.value = true
                }
            }
    ) {
        val infiniteTransition = rememberInfiniteTransition(label = "slide")
        val offsetX by infiniteTransition.animateFloat(
            initialValue = -20f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 500, easing = LinearOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            ), label = "slideX"
        )

        Icon(
            painter = painterResource(R.drawable.chevron_left),
            contentDescription = "Back",
            modifier = Modifier
                .padding(10.dp)
                .size(30.dp)
                .offset { IntOffset(offsetX.dp.roundToPx(), 0) }
                .align(Alignment.CenterEnd)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
        )
    }
}