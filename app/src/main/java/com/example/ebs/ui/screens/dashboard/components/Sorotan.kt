package com.example.ebs.ui.screens.dashboard.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ebs.data.structure.remote.ebs.articles.Article
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.components.structures.CenterRow
import com.example.ebs.ui.components.texts.TextContentM
import com.example.ebs.ui.components.texts.TextTitleM
import com.example.ebs.ui.components.texts.TextTitleS
import com.example.ebs.ui.screens.MainViewModel
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun Sorotan(
    viewModelMain: MainViewModel,
    articles: List<Article>
) {
    HeadlineDashboard {
        TextTitleM(
            "Sorotan",
            modifier = Modifier
                .padding(bottom = 15.dp, top = 10.dp))
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
            articles.size == 1 && articles[0] == Article() -> {
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
            articles.size == 1 && articles[0] == Article().copy(
                id = "Ups?! Tidak ada koneksi internet..."
            ) -> {
                CenterColumn (
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Text(
                        text = "Ups?! Tidak ada koneksi internet...",
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
                    ) { article: Article ->
                        CardDashboard(
                            photo = article.imageUrl ?: "",
                            modifier = Modifier
                                .height(100.dp)
                                .fillParentMaxWidth()
                                .clickable {
                                    viewModelMain.navHandler.articleFromMenu(article)
                                    viewModelMain.addViewCounter(article.id,article.viewCount)
                                }
                        ) {
                            CenterColumn(
                                vArr = Arrangement.SpaceEvenly,
                                hAli = Alignment.Start,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(top = 2.dp, bottom = 2.dp, end = 8.dp, start = 8.dp)
                            ) {
                                Column {
                                    TextTitleS(
                                        article.title,
                                        modifier = Modifier
                                            .height(40.dp),
                                        textAlign = TextAlign.Start
                                    )
                                }
                                CenterRow {
//                                    TextContentM(
//                                        article.id,
//                                        modifier = Modifier
//                                            .width(100.dp)
//                                            .height(15.dp)
//                                    )
//                                    Box(
//                                        modifier = Modifier
//                                            .padding(5.dp)
//                                            .size(5.dp)
//                                            .background(
//                                                Color.Gray,
//                                                shape = CircleShape
//                                            )
//                                    )
                                    TextContentM(
                                        article.createdAt.toLocalDateTime(TimeZone.currentSystemDefault()).date.toJavaLocalDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")),
                                        modifier = Modifier.height(18.dp)
                                    )
                                }
                            }
                        }
                        if(article == articles.last()) {
                            Spacer(modifier = Modifier.height(100.dp))
                        }
                    }
                }
            }
        }
    }
}