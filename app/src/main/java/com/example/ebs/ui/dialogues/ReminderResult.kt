package com.example.ebs.ui.dialogues

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Top
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ebs.R
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.components.structures.CenterRow
import com.example.ebs.ui.components.texts.TextContentL
import com.example.ebs.ui.components.texts.TextTitleS
import com.example.ebs.ui.navigation.NavigationHandler

@Composable
fun ReminderResult(
    reminder: MutableState<Boolean>,
    navHandler: NavigationHandler,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(24.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceBright
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        CenterColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CenterRow (
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(10.dp)
            ){
                Image(
                    painter = painterResource(R.drawable.star),
                    contentDescription = "star",
                    modifier = Modifier
                        .padding(5.dp)
                        .size(30.dp)
                        .align(Top),
                    contentScale = ContentScale.Crop
                )
                CenterColumn (
                    hAli = Alignment.Start,
                    vArr = Arrangement.Top,
                    modifier = Modifier
                        .align(Top)
                ){
                    TextTitleS(text = "Catatan:")
                    TextContentL(
                        text = "Estimasi harga dan saran berbasis AI dapat berubah tergantung kondisi fisik perangkat dan modelnya. Untuk hasil maksimal, simpan perangkat dalam keadaan utuh saat disetor.",
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
            CenterRow(
                hArr = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 5.dp)
            ){
                CenterRow(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 5.dp)
                        .border(
                            BorderStroke(1.dp, Color.Black),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable{ navHandler.back() }
                ){
                    Text("Batalkan",modifier = Modifier.padding(10.dp))
                }
                CenterRow(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 5.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable{ reminder.value = true }
                ){
                    Text("Konfirmasi",modifier = Modifier.padding(10.dp), color= Color.White)
                }
            }
        }
    }
}