package com.example.ebs.ui.face.components.inputs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun InputSpace(
    text: String,
    inpud: MutableState<String>,
    modifier: Modifier = Modifier.Companion
){
    val hide = remember { mutableStateOf(true) }
    TextField(
        value = inpud.value,
        onValueChange = { inpud.value = it },
        placeholder = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                textAlign = TextAlign.Companion.Center
            )
        },
        trailingIcon = { if(text == "Password") {
            Text(
                text = if (hide.value == true) "Show" else "Hide",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                textAlign = TextAlign.Companion.Center,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        hide.value = !hide.value
                    }
            )
        }
        },
        visualTransformation = if(text == "Password") { if (hide.value == true) PasswordVisualTransformation() else VisualTransformation.None }else VisualTransformation.None,
        modifier = modifier
            .fillMaxWidth(0.8f)
            .padding(8.dp)
    )
}