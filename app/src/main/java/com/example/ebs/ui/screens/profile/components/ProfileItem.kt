package com.example.ebs.ui.screens.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun ProfileItem(
    item: String,icon: Painter,
    modifier: Modifier = Modifier
){
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 24.dp)
    ) {
        Row (
            modifier = modifier
        ){
            Icon(
                painter = icon,
                contentDescription = null,
                tint = Color.LightGray,
                modifier = Modifier
                    .padding(end = 16.dp)
            )
            Text(item)
        }
    }
}