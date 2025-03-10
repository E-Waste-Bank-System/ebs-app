package com.example.ebs.ui.face.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ebs.ui.face.components.gradients.getGredienBackground
import com.example.ebs.ui.face.components.shapes.BotBarPage
import com.example.ebs.ui.face.components.structures.CenterColumn
import com.example.ebs.ui.navigation.NavigationHandler

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    signedIn: MutableState<String?>,
    navHandler: NavigationHandler,
    viewModel: DashboardViewModel = hiltViewModel(),
    //    viewModel: DashboardViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    BotBarPage(
        navController = navController,
        signedIn = signedIn,
        modifier = Modifier
            .background(getGredienBackground(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.background
        )),
        hazeState = viewModel.hazeState
    ){
        val requests by viewModel.requests.collectAsState()
        val articles by viewModel.articles.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()
        val pullRefreshState = rememberPullToRefreshState()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullToRefresh(
                    state = pullRefreshState,
                    isRefreshing = isLoading,
                    onRefresh = {
                        viewModel.refresh()
                    }
                ),
            contentAlignment = Alignment.TopStart
        ) {
            CenterColumn (
                modifier = Modifier
                    .scrollable(    
                        state = rememberScrollableState { 0f },
                        orientation = Orientation.Vertical
                    )
            ){
                Greeting(navHandler, signedIn)
                Trending(requests)
                Sorotan(articles)
            }
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = isLoading,
                state = pullRefreshState
            )
        }
    }
}

//@DarkPreview
//@Composable
//fun PreviewCardDashboard1() {
//    EBSTheme {
//        CenterColumn {
//            Trending(angka = 1)
//            Sorotan()
//        }
//    }
//}
//
//@LightPreview
//@Composable
//fun PreviewCardDashboard2() {
//    EBSTheme {
//        CenterColumn {
//            Trending(angka = 1)
//            Sorotan()
//        }
//    }
//}
