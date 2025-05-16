package com.example.ebs.ui.components.shapes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun MyIconImage(
    painter: Painter,
    contentDescription: String,
    modifier: Modifier = Modifier.Companion
){
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier
            .size(50.dp)
    )
}