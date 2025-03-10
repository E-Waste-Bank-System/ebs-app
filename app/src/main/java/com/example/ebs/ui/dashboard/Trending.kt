package com.example.ebs.ui.face.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ebs.data.structure.Requests
import com.example.ebs.ui.face.components.shapes.Indicator
import com.example.ebs.ui.face.components.structures.CenterColumn
import com.example.ebs.ui.face.components.texts.TextContentS
import com.example.ebs.ui.face.components.texts.TextTitleM
import com.example.ebs.ui.face.components.texts.TextTitleS
import kotlinx.datetime.Instant

@Composable
fun Trending(
    requests: List<Requests>
){
    HeadlineDashboard {
        TextTitleM("Terkini")
        TextTitleS("See All...")
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        when {
            requests.isEmpty() -> {
                CenterColumn (
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(20.dp)
                    )
                    Text(
                        text = "Loading...",
                        color = Color.Gray
                    )
                }
            }
            requests.size == 1 && requests[0] == Requests(
                id = "1",
                userId = "impossible_user",
                status = "Generated",
                description = "Impossible request for placeholder",
                imageUrl = "https://example.com/impossible.png",
                createdAt = Instant.fromEpochMilliseconds(0)
            ) -> {
                CenterColumn (
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Text(
                        text = "No trending requests available.",
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
                        requests,
                        key = { request -> request.id },
                    ) { request: Requests ->
                        CardDashboard(
                            modifier = Modifier
                                .height(125.dp)
                                .width(240.dp)
                        ) {
                            CenterColumn(
                                vArr = Arrangement.SpaceBetween,
                                hAli = Alignment.Start,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(top = 10.dp, bottom = 10.dp, end = 5.dp)
                            ) {
                                Column {
                                    TextContentS("Kategori: Smarphone $request.status")
                                    TextContentS("Jumlah: 1")
                                    TextContentS("Prediksi Harga: Rp. 50.000")
                                }
                                Indicator("Pending", Color(0xFFE27700))
                            }
                        }
                    }
                }
            }
        }
    }
}