package com.example.ebs.ui.components.structures

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CenterRow(
    hArr: Arrangement.Horizontal = Arrangement.Center,
    vAli: Alignment.Vertical = Alignment.Companion.CenterVertically,
    modifier: Modifier = Modifier.Companion,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        horizontalArrangement = hArr,
        verticalAlignment = vAli,
        modifier = modifier
    ) {
        content()
    }
}