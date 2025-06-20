package com.example.ebs.ui.components.texts

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun TextTitleXL(text: Any, modifier: Modifier = Modifier, textAlign: TextAlign = TextAlign.Center){
    val style = MaterialTheme.typography.headlineMedium.copy(
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 33.sp,
        fontWeight = FontWeight.Bold
    )
    Text(
        checkTag(text),
        style = style,
        overflow = TextOverflow.Ellipsis,
        textAlign = textAlign,
        modifier = modifier
    )
}