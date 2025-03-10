package com.example.ebs.ui.face.components.texts

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun TextContentS(text: Any, modifier: Modifier = Modifier.Companion, mod: Boolean = false) {
    val style = MaterialTheme.typography.labelSmall.copy(
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 10.sp
    )
    if (mod) {
        Text(
            text as AnnotatedString,
            style = style,
            overflow = TextOverflow.Companion.Ellipsis,
            letterSpacing = 0.1.sp,
            modifier = modifier
        )
    }
    else {
        Text(
            text as String,
            style = style,
            overflow = TextOverflow.Companion.Ellipsis,
            letterSpacing = 0.1.sp,
            modifier = modifier
        )
    }
}