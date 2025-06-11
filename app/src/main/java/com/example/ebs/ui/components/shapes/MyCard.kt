package com.example.ebs.ui.components.shapes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MyCard(
    modifier: Modifier = Modifier.Companion,
    border: BorderStroke = BorderStroke(
        width = 0.9.dp,
        MaterialTheme.colorScheme.onSurface
    ),
    content: @Composable () -> Unit
){
    Card(
        border = border,
        modifier = modifier
            .padding(vertical = 10.dp)
    ) {
        content()
    }
}