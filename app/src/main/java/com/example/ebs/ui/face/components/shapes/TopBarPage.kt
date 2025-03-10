package com.example.ebs.ui.face.components.shapes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ebs.ui.face.components.structures.CenterColumn

@Composable
fun TopBarPage(
    title: String,
    modifier: Modifier = Modifier.Companion,
    content: @Composable () -> Unit
){
    Scaffold(
        topBar = {
            TopBer(title)
        },
    ) { padding ->
        CenterColumn(
            vArr = Arrangement.Top,
            modifier = Modifier.Companion
                .fillMaxSize()
                .padding(padding)
        ) {
            content()
        }
    }
}