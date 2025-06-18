package com.example.ebs.ui.components.texts

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun TextContentM(text: Any, modifier: Modifier = Modifier, textAlign: TextAlign = TextAlign.Center) {
    val style = MaterialTheme.typography.labelSmall.copy(
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 12.sp
    )
    Text(
        checkTag(text),
        style = style,
        overflow = TextOverflow.Companion.Ellipsis,
        modifier = modifier,
        textAlign = textAlign
    )
}