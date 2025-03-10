package com.example.ebs.ui.face.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ebs.data.structure.Articles
import com.example.ebs.ui.face.components.structures.CenterColumn
import com.example.ebs.ui.face.components.structures.CenterRow
import com.example.ebs.ui.face.components.texts.TextContentL
import com.example.ebs.ui.face.components.texts.TextContentM
import com.example.ebs.ui.face.components.texts.TextTitleM
import kotlinx.datetime.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Sorotan(
    articles: List<Articles>
) {
    HeadlineDashboard {
        TextTitleM("Sorotan")
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        when {
            articles.isEmpty() -> {
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
            articles.size == 1 && articles[0] == Articles(
                id = "1",
                title = "Sample Article",
                imageUrl = "https://example.com/image.jpg",
                content = "This is a sample article content.",
                createdAt = Instant.parse("2023-10-01T00:00:00Z")
            ) -> {
                CenterColumn (
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Text(
                        text = "No trending articles available.",
                        modifier = Modifier
                            .padding(20.dp),
                        color = Color.Gray
                    )
                }
            }

            else -> {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    items(
                        articles,
                        key = { article -> article.id },
                    ) { article: Articles ->
                        CardDashboard(
                            photo = article.imageUrl,
                            modifier = Modifier
                                .height(100.dp)
                                .fillParentMaxWidth(0.9f)
                        ) {
                            CenterColumn(
                                vArr = Arrangement.SpaceEvenly,
                                hAli = Alignment.Start,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(top = 2.dp, bottom = 2.dp, end = 8.dp, start = 8.dp)
                            ) {
                                Column {
                                    TextContentL(
                                        article.title,
                                        modifier = Modifier
                                            .width(100.dp)
                                            .height(15.dp)
                                    )
                                }
                                CenterRow {
                                    TextContentM(
                                        article.id,
                                        modifier = Modifier
                                            .width(100.dp)
                                            .height(15.dp)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .size(5.dp)
                                            .background(
                                                Color.Gray,
                                                shape = CircleShape
                                            )
                                    )
                                    TextContentM(
                                        article.createdAt.toString(),
                                        modifier = Modifier
                                            .width(100.dp)
                                            .height(15.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}