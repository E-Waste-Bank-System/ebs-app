package com.example.ebs.ui.components.structures

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CenterColumn(
    vArr: Arrangement.Vertical = Arrangement.Center,
    hAli: Alignment.Horizontal = Alignment.Companion.CenterHorizontally,
    modifier: Modifier = Modifier.Companion,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        verticalArrangement = vArr,
        horizontalAlignment = hAli,
        modifier = modifier
    ) {
        content()
    }
}