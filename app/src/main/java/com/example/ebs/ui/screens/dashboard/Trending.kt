package com.example.ebs.ui.screens.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.ebs.R
import com.example.ebs.data.structure.remote.ebs.detections.head.Detection
import com.example.ebs.data.structure.remote.ebs.detections.head.ScanResponse
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.components.texts.TextContentL
import com.example.ebs.ui.components.texts.TextContentM
import com.example.ebs.ui.components.texts.TextTitleM
import com.example.ebs.ui.components.texts.TextTitleS
import com.example.ebs.ui.screens.MainViewModel
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toLocalDateTime

@Composable
fun Trending(
    viewModelAuth: MainViewModel,
    history: List<Detection>
){
    HeadlineDashboard {
        TextTitleM(
            stringResource(R.string.history),
            modifier = Modifier
                .padding(bottom = 15.dp, top = 10.dp)
        )
        TextContentL(
            buildAnnotatedString {
            withStyle(SpanStyle(color = Color.Gray)) {
                append(stringResource(R.string.seeall))
            }
        }, modifier = Modifier
                .padding(bottom = 15.dp, top = 10.dp)
                .clickable{
                    viewModelAuth.navHandler.riwayat()
                }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        when {
            history.isEmpty() -> {
                CenterColumn (
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(20.dp)
                    )
                    Text(text = "Loading...", color = Color.Gray)
                }
            }
            history.size == 1 && history[0] == Detection() -> {
                CenterColumn (
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Text(
                        text = stringResource(R.string.noTrending),
                        modifier = Modifier
                            .padding(20.dp),
                        color = Color.Gray
                    )
                }
            }
            else -> {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    items(
                        history.size,
                        key = { hist -> history[hist].id },
                    ) { item ->
                        CardDashboard(
                            photo = history[item].imageUrl,
                            modifier = Modifier
                                .height(125.dp)
                                .width(240.dp)
                                .clickable {
                                    viewModelAuth.navHandler.detailFromMenu(
                                        ScanResponse().copy(
                                            id = history[item].id
                                        ),
                                        history[item].imageUrl
                                    )
                                }
                        ) {
                            CenterColumn(
                                vArr = Arrangement.SpaceBetween,
                                hAli = Alignment.Start,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(top = 10.dp, bottom = 10.dp, end = 5.dp, start = 10.dp)
                            ) {
                                Column {
                                    TextContentM(
                                        history[item].createdAt.toLocalDateTime(TimeZone.currentSystemDefault()).date.toJavaLocalDate().format(java.time.format.DateTimeFormatter.ofPattern("dd MMMM yyyy")),
                                        modifier = Modifier.height(20.dp)
                                    )
                                    if(history[item].objects.isEmpty()) {
                                        TextTitleS(
                                            "Tidak ada yang terdeteksi",
                                            textAlign = TextAlign.Start
                                        )
                                    } else {
                                        TextTitleS(history[item].objects.first().category, textAlign = TextAlign.Start)
                                    }
                                }
//                                Indicator("Pending", Color(0xFFE27700))
                            }
                        }
                    }
                }
            }
        }
    }
}