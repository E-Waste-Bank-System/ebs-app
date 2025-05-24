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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ebs.R
import com.example.ebs.data.structure.remote.book.DataTest
import com.example.ebs.data.structure.remote.ebs.detections.DataDetections
import com.example.ebs.data.structure.remote.ebs.detections.Detection
import com.example.ebs.data.structure.remote.ebs.detections.Histories
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.components.structures.CenterRow
import com.example.ebs.ui.components.texts.TextTitleS
import com.example.ebs.ui.navigation.BotBarPage
import com.example.ebs.ui.screens.MainViewModel
import com.example.ebs.ui.screens.dashboard.CardDashboard
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetectionListScreen(
    navController: NavController,
    viewModelAuth: MainViewModel,
    viewModel: DetectionListViewModel = hiltViewModel()
) {
    Log.d("Route", "This is Histories")

    val history by viewModelAuth.history.collectAsState()
    val isLoading by viewModelAuth.isLoading.collectAsState()
    val pullRefreshState = rememberPullToRefreshState()

    BotBarPage(
        navController = navController,
        modifier = Modifier.padding(top = 50.dp),
        hazeState = viewModelAuth.hazeState
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullToRefresh(
                    state = pullRefreshState,
                    isRefreshing = isLoading,
                    onRefresh = {
                        viewModelAuth.refresh()
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

            history.size == 1 && history[0] == Histories() -> {
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
            } else -> {
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
                                photo = history[item].objects.firstOrNull()?.imageUrl ?: "",
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
                                            TextTitleS(history[item].createdAt.toLocalDateTime(
                                                TimeZone.currentSystemDefault()).date.toString().split("-").reversed().joinToString("/"))
                                            if(history[item].objects.isEmpty()) {
                                                TextTitleS("Tidak ada yang terdeteksi")
                                            } else {
                                                TextTitleS(history[item].objects.first().category)
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
                                            .align(Alignment.BottomEnd)
                                            .clickable {
                                                viewModelAuth.navHandler.detailFromMenu(
                                                    DataDetections(
                                                        "",
                                                        history[item].objects.firstOrNull()?.scanId ?: "",
                                                        history[item].objects.map { it ->
                                                            Detection(
                                                                it.scanId,
                                                                it.imageUrl,
                                                                it.category,
                                                                it.confidence,
                                                                it.regressionResult,
                                                                it.description,
                                                                it.suggestion,
                                                                it.riskLvl,
                                                                it.detectionSource
                                                            )
                                                        }
                                                    )
                                                )
                                            }
                                        ) {
                                            Box(
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    painterResource(R.drawable.open_in_new),
                                                    contentDescription = "Notification",
                                                    tint = MaterialTheme.colorScheme.onSurface,
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