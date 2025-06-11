package com.example.ebs.ui.components.gradients

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun getGredienBackground(color1: Color, color2: Color): Brush {
    return Brush.Companion.linearGradient(
        colors = listOf(color1, color2),
        start = Offset(0f, -3800f),
        end = Offset(0f, 1800f),
    )
}