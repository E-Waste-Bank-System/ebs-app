package com.example.ebs.ui.components.texts

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun TextContentS(text: Any, modifier: Modifier = Modifier, mod: Boolean = false, textAlign: TextAlign = TextAlign.Center) {
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
            modifier = modifier,
            textAlign = textAlign
        )
    }
    else {
        Text(
            text as String,
            style = style,
            overflow = TextOverflow.Companion.Ellipsis,
            letterSpacing = 0.1.sp,
            modifier = modifier,
            textAlign = textAlign
        )
    }
}