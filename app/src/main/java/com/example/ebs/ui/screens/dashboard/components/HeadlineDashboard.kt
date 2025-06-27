package com.example.ebs.ui.screens.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ebs.ui.components.structures.CenterRow

@Composable
fun HeadlineDashboard(
    hArr: Arrangement.Horizontal = Arrangement.SpaceBetween,
    content: @Composable () -> Unit
){
    CenterRow(
        hArr = hArr,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp, top = 10.dp)
    ) {
        content()
    }
}