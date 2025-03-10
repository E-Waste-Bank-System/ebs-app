package com.example.ebs.ui.face.components.gradients

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun getGredienButton(color1: Color, color2: Color): Brush {
    return Brush.Companion.linearGradient(
        colors = listOf(color1, color2),
        start = Offset(-100f, -100f),
        end = Offset(600f, 600f),
    )
}