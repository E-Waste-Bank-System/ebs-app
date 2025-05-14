package com.example.ebs.ui.face.history

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ebs.R
import com.example.ebs.data.repositories.remote.DataTest
import com.example.ebs.ui.navigation.BotBarPage
import com.example.ebs.ui.face.components.structures.CenterColumn
import com.example.ebs.ui.face.components.structures.CenterRow
import com.example.ebs.ui.face.components.shapes.Indicator
import com.example.ebs.ui.face.components.texts.TextContentS
import com.example.ebs.ui.face.dashboard.CardDashboard
import com.example.ebs.ui.navigation.NavigationHandler

@Composable
fun DetectionListScreen(
    navController: NavController,
    signedIn: MutableState<String?>,
    navHandler: NavigationHandler,
    modifier: Modifier = Modifier,
    viewModel: DetectionListViewModel = hiltViewModel()
) {
    BotBarPage(
        navController = navController,
        signedIn = signedIn,
        hazeState = viewModel.hazeState
    ) {
        val jumlah = 10
        val barang: MutableState<List<String>> = remember { mutableStateOf(emptyList()) }
        barang.value = List(jumlah) { "Barang ${it + 1}" }
        LazyColumn {
            items(jumlah) {
                CardDashboard(
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
                                .padding(top = 10.dp, bottom = 10.dp, end = 5.dp)
                        ) {
                            Column {
                                TextContentS("Kategori: Smarphone")
                                TextContentS("Jumlah: 1")
                                TextContentS("Prediksi Harga: Rp. 50.000")
                            }
                            Indicator("Pending", Color(0xFFE27700))
                        }
                        CenterRow(
                            modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = 5.dp)
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.background)
                                .align(Alignment.BottomEnd)
                                .clickable {
                                    Log.e("Ini","Ini keklikan???? ${barang.value[it]}")
                                    navHandler.detailFromMenu(barang = barang.value[it])
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