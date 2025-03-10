package com.example.ebs.ui.face.components.structures

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CenterRow(
    hArr: Arrangement.Horizontal = Arrangement.Center,
    vAli: Alignment.Vertical = Alignment.Companion.CenterVertically,
    modifier: Modifier = Modifier.Companion,
    content: @Composable () -> Unit
) {
    Row(
        horizontalArrangement = hArr,
        verticalAlignment = vAli,
        modifier = modifier
    ) {
        content()
    }
}