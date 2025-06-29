package com.example.ebs.ui.components.texts

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp

@Composable
fun TextTitleS(text: Any, modifier: Modifier = Modifier, textAlign: TextAlign = TextAlign.Center){
    val style = MaterialTheme.typography.bodyLarge.copy(
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 12.sp,
        fontWeight = FontWeight.Companion.Bold
    )
    Text(
        checkTag(text),
        style = style,
        overflow = TextOverflow.Companion.Ellipsis,
        textAlign = textAlign,
        modifier = modifier,
        lineHeight = TextUnit(value = 18f, type = TextUnitType.Sp)
    )
}