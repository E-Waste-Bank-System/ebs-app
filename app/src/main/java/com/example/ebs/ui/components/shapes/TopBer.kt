package com.example.ebs.ui.components.shapes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ebs.R
import com.example.ebs.ui.components.texts.TextTitleL
import com.example.ebs.ui.navigation.NavigationHandler

@Composable
fun TopBer(
    title: Any = "Not Set",
    navHandler: NavigationHandler,
    customBack: Color,
    mod: Boolean = false
){
    Box (
        modifier = Modifier
            .background(if (customBack!=MaterialTheme.colorScheme.background) customBack else MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 32.dp,
                bottom = 16.dp
            )
    ){
        Icon(
            painter = painterResource(R.drawable.chevron_left),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(40.dp)
                .clickable{
                    navHandler.back()
                },
            tint = if (mod) Color.White else MaterialTheme.colorScheme.onBackground
        )
        TextTitleL(
            text = title,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}