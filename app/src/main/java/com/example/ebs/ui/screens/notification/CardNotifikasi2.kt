package com.example.ebs.ui.screens.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ebs.ui.components.structures.CenterRow

@Composable
fun CardNotifikasi2(
    modifier: Modifier = Modifier.Companion,
    content: @Composable () -> Unit,
){
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.Companion
            .padding(5.dp)
    ) {
        CenterRow(
            hArr = Arrangement.Start,
            modifier = modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(15.dp)
        ) {
            content()
        }
    }
}