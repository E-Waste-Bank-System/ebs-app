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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Sorotan(
    viewModelAuth: MainViewModel,
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
                            photo = article.imageUrl,
                            modifier = Modifier
                                .height(100.dp)
                                .clickable {
                                    viewModelAuth.navHandler.articleFromMenu(article)
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
                                            .width(200.dp)
                                            .height(25.dp),
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
                                        article.createdAt.toLocalDateTime(TimeZone.currentSystemDefault()).date.toString().split("-").reversed().joinToString("/"),
                                        modifier = Modifier.height(15.dp)
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