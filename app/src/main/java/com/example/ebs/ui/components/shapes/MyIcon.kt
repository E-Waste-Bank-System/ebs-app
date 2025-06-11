package com.example.ebs.ui.components.shapes

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun MyIcon(
    painter: Painter,
    contentDescription: String,
    tint: Color = MaterialTheme.colorScheme.secondary,
    modifier: Modifier = Modifier.Companion
){
    Icon(
        painter = painter,
        contentDescription = contentDescription,
        tint = tint,
        modifier = modifier
    )
}