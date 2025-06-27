package com.example.ebs.ui.screens.historyDetail

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ebs.R
import com.example.ebs.data.structure.remote.book.DataTest
import com.example.ebs.data.structure.remote.ebs.detections.head.Detection
import com.example.ebs.data.structure.remote.ebs.detections.head.ScanResponse
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.components.structures.CenterRow
import com.example.ebs.ui.components.texts.TextContentM
import com.example.ebs.ui.components.texts.TextTitleS
import com.example.ebs.ui.navigation.BotBarPage
import com.example.ebs.ui.screens.MainViewModel
import com.example.ebs.ui.screens.dashboard.components.CardDashboard
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetectionListScreen(
    navController: NavController,
    viewModelMain: MainViewModel,
    viewModel: DetectionListViewModel = hiltViewModel()
) {
    Log.d("Route", "This is Histories")

    val history by viewModelMain.history.collectAsState()
    val isLoading by viewModelMain.isLoading.collectAsState()
    val pullRefreshState = rememberPullToRefreshState()

    BotBarPage(
        navController = navController,
        hazeState = viewModelMain.hazeState
    ) {
        Spacer(modifier = Modifier.statusBarsPadding())
        Spacer(modifier = Modifier.statusBarsPadding())
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullToRefresh(
                    state = pullRefreshState,
                    isRefreshing = isLoading,
                    onRefresh = {
                        viewModelMain.refresh()
                    }
                ),
            contentAlignment = Alignment.TopStart
        ) {
            when {
            history.isEmpty() -> {
                CenterColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .scrollable(
                            state = rememberScrollableState { 0f },
                            orientation = Orientation.Vertical
                        )
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(20.dp)
                    )
                    Text(text = "Loading...", color = Color.Gray)
                }
            }

            history.size == 1 && history[0] == Detection() -> {
                CenterColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .scrollable(
                            state = rememberScrollableState { 0f },
                            orientation = Orientation.Vertical
                        )
                ) {
                    Text(
                        text = stringResource(R.string.noTrending),
                        modifier = Modifier
                            .padding(20.dp),
                        color = Color.Gray
                    )
                }
            }
            history.size == 1 && history[0] == Detection().copy(
                id = "Ups?! Tidak ada koneksi internet..."
            ) -> {
                CenterColumn (
                    modifier = Modifier
                        .fillMaxSize()
                ){
                    Text(
                        text = "Ups?! Tidak ada koneksi internet...",
                        modifier = Modifier
                            .height(125.dp)
                            .padding(20.dp),
                        color = Color.Gray
                    )
                }
            }
                else -> {
                    CenterColumn(
                        vArr = Arrangement.Top,
                        modifier = Modifier
                            .fillMaxSize()
                            .scrollable(
                                state = rememberScrollableState { 0f },
                                orientation = Orientation.Vertical
                            )
                    ) {
                    LazyColumn {
                        items(
                            history.size,
                            key = { hist -> history[hist].id },
                        ) { item ->
                            CardDashboard(
                                photo = history[item].imageUrl,
                                modifier = Modifier
                                    .height(125.dp)
                                    .fillMaxWidth(0.85f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                ) {
                                    CenterColumn(
                                        vArr = Arrangement.SpaceBetween,
                                        hAli = Alignment.Start,
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .padding(
                                                top = 10.dp,
                                                bottom = 10.dp,
                                                end = 5.dp,
                                                start = 10.dp
                                            )
                                    ) {
                                        Column {
                                            TextTitleS(
                                                history[item]
                                                    .createdAt
                                                    .toLocalDateTime(
                                                        TimeZone
                                                            .currentSystemDefault()
                                                    )
                                                    .date
                                                    .toJavaLocalDate()
                                                    .format(
                                                        DateTimeFormatter
                                                            .ofPattern(
                                                                "dd MMMM yyyy"
                                                            )),
                                                modifier = Modifier.height(22.dp),
                                                textAlign = TextAlign.Start
                                            )
                                            Spacer(modifier = Modifier.height(5.dp))
                                            if(history[item].objects.isEmpty()) {
                                                TextTitleS(
                                                    "Tidak ada yang terdeteksi",
                                                    textAlign = TextAlign.Start,
                                                    modifier = Modifier.width(100.dp)

                                                )
                                            } else {
                                                TextContentM(
                                                    history[item].objects.joinToString(", ") { it.category },
                                                    textAlign = TextAlign.Start,
                                                    modifier = Modifier.width(100.dp)
                                                )
                                            }
//                                            TextTitleS(history[item].status)
    //                                        TextContentS("Kategori: Smarphone")
    //                                        TextContentS("Jumlah: 1")
    //                                        TextContentS("Prediksi Harga: Rp. 50.000")
                                        }
//                                        Indicator("Pending", Color(0xFFE27700))
                                    }
                                    CenterRow(
                                        modifier = Modifier
                                            .padding(horizontal = 10.dp, vertical = 5.dp)
                                            .size(40.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.background)
                                            .align(Alignment.TopEnd)
                                            .clickable {
                                                viewModelMain
                                                    .addDeletedScans(history[item].id)
                                            }
                                    ) {
                                        Box(
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                painterResource(R.drawable.delete_forever),
                                                contentDescription = "Notification",
                                                tint =
                                                    MaterialTheme
                                                        .colorScheme.errorContainer,
                                            )
                                        }
                                    }
                                    CenterRow(
                                        modifier = Modifier
                                            .padding(horizontal = 10.dp, vertical = 5.dp)
                                            .size(40.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme
                                                .colorScheme
                                                .background
                                            ).align(Alignment.BottomEnd)
                                            .clickable {
                                                viewModelMain
                                                    .navHandler
                                                    .detailFromMenu(
                                                        ScanResponse().copy(
                                                            id = history[item].id
                                                        ),
                                                        history[item].imageUrl
                                                    )
                                            }
                                        ) {
                                            Box(
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    painterResource(R.drawable.open_in_new),
                                                    contentDescription = "Notification",
                                                    tint =
                                                        MaterialTheme
                                                            .colorScheme
                                                            .onSurface,
                                                )
                                            }
                                        }
                                    }
                                }
                                if(item == history.size - 1) {
                                    Spacer(modifier = Modifier.height(100.dp))
                                }
                            }
                        }
                    }
                }
            }
            PullToRefreshDefaults.Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = isLoading,
                state = pullRefreshState
            )
        }
    }

//    CenterColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .hazeSource(hazeState)
//    ) {
//        val detectionListUiState by viewModel.detectionListUiState.collectAsState()
//        println("DetectionListScreen: $detectionListUiState")
//        when (detectionListUiState) {
//            is DetectionListUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
//            is DetectionListUiState.Success -> ResultScreen((detectionListUiState as DetectionListUiState.Success).dataTestList, modifier)
//            else -> ErrorScreen(viewModel::getData, modifier = modifier.fillMaxSize())
//        }
//    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Icon(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.walk),
        contentDescription = "R.string.loading"
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    CenterColumn(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.missed),
            contentDescription = ""
        )
        Text("Failed", modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text("Retry")
        }
    }
}

@Composable
fun ResultScreen(photos: DataTest, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(text = photos.toString())
    }
}