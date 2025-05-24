package com.example.ebs.ui.screens.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.ebs.R
import com.example.ebs.ui.components.structures.CenterRow

@Composable
fun CardDashboard(
    modifier: Modifier = Modifier,
    photo: String = "",
    content: @Composable () -> Unit,
){
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(5.dp)
    ) {
        CenterRow(
            hArr = Arrangement.Start,
            modifier = modifier
                .background(if(isSystemInDarkTheme()) Color(0xFF102232) else MaterialTheme.colorScheme.surface)
                .padding(vertical = 10.dp, horizontal = 10.dp)
        ) {
            if (photo == "") {
                Image(
                    painterResource(R.drawable.nopicture),
                    contentDescription = "Icon",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            } else {
                Image(
                    painter = rememberAsyncImagePainter(photo),
                    contentDescription = "Account Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(100.dp)
                        .width(110.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }
            content()
        }
    }
}