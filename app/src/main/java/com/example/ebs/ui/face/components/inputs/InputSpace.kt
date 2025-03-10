package com.example.ebs.ui.face.components.inputs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun InputSpace(
    text: String,
    modifier: Modifier = Modifier.Companion
){
    var inpud by rememberSaveable { mutableStateOf("") }
    TextField(
        value = inpud,
        onValueChange = { inpud = it },
        placeholder = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                textAlign = TextAlign.Companion.Center
            )
        },
        modifier = modifier
            .fillMaxWidth(0.8f)
            .padding(8.dp)
    )
}