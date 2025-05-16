package com.example.ebs.ui.components.texts

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun TextTitleS(text: Any, modifier: Modifier = Modifier, mod: Boolean = false){
    val style = MaterialTheme.typography.bodyLarge.copy(
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 14.sp,
        fontWeight = FontWeight.Companion.Bold
    )
    if (mod) {
        Text(
            text as AnnotatedString,
            style = style,
            overflow = TextOverflow.Companion.Ellipsis,
            textAlign = TextAlign.Companion.Center,
            modifier = modifier
        )
    }
    else {
        Text(
            text as String,
            style = style,
            overflow = TextOverflow.Companion.Ellipsis,
            textAlign = TextAlign.Companion.Center,
            modifier = modifier
        )
    }
}