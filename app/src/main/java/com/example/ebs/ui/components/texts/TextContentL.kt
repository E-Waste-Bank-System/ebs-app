package com.example.ebs.ui.components.texts

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun TextContentL(text: Any, modifier: Modifier = Modifier, textAlign: TextAlign = TextAlign.Center) {
    val style = MaterialTheme.typography.bodySmall.copy(
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Companion.Bold
    )
    Text(
        checkTag(text),
        style = style,
        overflow = TextOverflow.Companion.Ellipsis,
        modifier = modifier,
        textAlign = textAlign
    )
}

