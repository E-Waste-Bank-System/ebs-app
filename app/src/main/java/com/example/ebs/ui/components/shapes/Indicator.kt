package com.example.ebs.ui.components.shapes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.ebs.ui.components.structures.CenterRow
import com.example.ebs.ui.components.texts.TextContentS

@Composable
fun Indicator(
    text: String,
    color: Color
){
    CenterRow(
        modifier = Modifier.Companion
            .height(20.dp)
            .clip(CircleShape)
            .background(
                color.copy(
                    alpha = 0.15f
                )
            )
    ) {
        CenterRow(
            modifier = Modifier.Companion
                .padding(horizontal = 8.dp)
        ) {
            CenterRow(
                modifier = Modifier.Companion
                    .padding(end = 4.dp)
                    .size(5.dp)
                    .clip(CircleShape)
                    .background(
                        color
                    )
            ) {}
            TextContentS(buildAnnotatedString {
                withStyle(SpanStyle(color = color)) {
                    append(text)
                }
            })
        }
    }
}