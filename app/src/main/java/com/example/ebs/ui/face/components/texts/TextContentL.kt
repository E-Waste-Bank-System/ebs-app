package com.example.ebs.ui.face.components.texts

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun TextContentL(text: Any, modifier: Modifier = Modifier, mod: Boolean = false) {
    val style = MaterialTheme.typography.bodySmall.copy(
        color = MaterialTheme.colorScheme.onBackground,
        fontWeight = FontWeight.Companion.Bold
    )
    if (mod) {
        Text(
            text as AnnotatedString,
            style = style,
            overflow = TextOverflow.Companion.Ellipsis,
            modifier = modifier
        )
    }
    else {
        Text(
            text as String,
            style = style,
            overflow = TextOverflow.Companion.Ellipsis,
            modifier = modifier
        )
    }
}