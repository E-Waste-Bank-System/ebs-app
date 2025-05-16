package com.example.ebs.ui.face.dashboard

import android.util.Log
import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ebs.data.repositories.UserPreferencesRepository
import com.example.ebs.ui.face.AuthViewModel
import com.example.ebs.ui.face.components.gradients.getGredienBackground
import com.example.ebs.ui.navigation.BotBarPage
import com.example.ebs.ui.face.components.structures.CenterColumn
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    userPref: UserPreferencesRepository,
    viewModel: DashboardViewModel = hiltViewModel(),
    viewModelAuth: AuthViewModel = hiltViewModel(),
    //    viewModel: DashboardViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    viewModelAuth.initializeNavHandler(navController)
    val checkIn = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val token = userPref.authToken.firstOrNull()
        viewModelAuth.updateLocalCred(token ?: "")
        Log.e("TAG", "SecondCredCheck: ${viewModelAuth.localCred.take(10)}")
        checkIn.value = true
    }

    Log.d("Route", "This is Dashboard")
    if(viewModelAuth.authManagerState.isSignedIn() && checkIn.value) {
        val exitDialogue = remember { mutableStateOf(false) }

        BackHandler { exitDialogue.value = true }

        if (exitDialogue.value) {
            viewModelAuth.navHandler.exitDialogue()
            exitDialogue.value = false
        }

        BotBarPage(
            navController = navController,
            modifier = Modifier
                .background(
                    getGredienBackground(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.background
                    )
                ),
            hazeState = viewModel.hazeState
        ) {
//            val requests by viewModel.requests.collectAsState()
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
                CenterColumn(
                    modifier = Modifier
                        .scrollable(
                            state = rememberScrollableState { 0f },
                            orientation = Orientation.Vertical
                        )
                ) {
                    Greeting(viewModelAuth.navHandler, viewModelAuth.localCred)
                    Trending(articles)
                    Sorotan(articles)
                }
                Indicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    isRefreshing = isLoading,
                    state = pullRefreshState
                )
            }
        }
    } else {
        LaunchedEffect(Unit) {
            delay(5000)
            Log.e("tim out","Ups checknya kelamaan")
            viewModelAuth.navHandler.signInFromWelcome()
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
