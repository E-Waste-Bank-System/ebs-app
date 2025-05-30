package com.example.ebs.ui.face.components.inputs

import android.R.attr.onClick
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.ebs.ui.face.components.gradients.getGredienButton
import com.example.ebs.ui.face.components.texts.TextTitleS

@Composable
fun AestheticButton(
    content: @Composable BoxScope.() -> Unit = {},
    onClick: () -> Unit
){
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.Companion
            .fillMaxWidth(0.8f)
            .height(60.dp)
            .clickable(
                onClick = onClick
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    getGredienButton(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
                    )
                )
        ) {
            content()
        }
    }
}
