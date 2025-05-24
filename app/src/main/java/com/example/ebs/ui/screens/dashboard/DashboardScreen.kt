package com.example.ebs.ui.screens.dashboard

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.ebs.R
import com.example.ebs.data.repositories.UserPreferencesRepository
import com.example.ebs.ui.components.gradients.getGredienBackground
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.navigation.BotBarPage
import com.example.ebs.ui.screens.MainViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    userPref: UserPreferencesRepository,
    viewModelAuth: MainViewModel,
    viewModel: DashboardViewModel = hiltViewModel(),
    //    viewModel: DashboardViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val loadStatus = rememberSaveable { mutableStateOf(false) }
    val check = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if (viewModelAuth.firstOpen) {
            Log.e("TAG", "firstOpen? ${viewModelAuth.firstOpen}")
            viewModelAuth.firstOpen = false
            coroutineScope {
                val refreshJob = async { viewModelAuth.refresh() }
                val userDataJob = async { viewModelAuth.getUserData() }
                val delayJob = async { delay(2000) }
                val checkJob = async { viewModelAuth.authManagerState.isSignedIn() }
                refreshJob.await()
                userDataJob.await()
                delayJob.await()
                check.value = checkJob.await()
                delay(1000)
            }
            loadStatus.value = check.value
            if (!loadStatus.value) {
                viewModelAuth.navHandler.signInFromWelcome()
            }
        } else {
            loadStatus.value = true
        }
    }

    Log.d("Route", "This is Dashboard")
    if (loadStatus.value) {
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
                )
                .padding(top = 25.dp),
            hazeState = viewModelAuth.hazeState
        ) {
            val history by viewModelAuth.history.collectAsState()
            val articles by viewModelAuth.articles.collectAsState()
            val isLoading by viewModelAuth.isLoading.collectAsState()
            val pullRefreshState = rememberPullToRefreshState()
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
                CenterColumn(
                    modifier = Modifier
                        .scrollable(
                            state = rememberScrollableState { 0f },
                            orientation = Orientation.Vertical
                        )
                ) {
                    Greeting(viewModelAuth)
                    Trending(viewModelAuth,history)
                    Sorotan(viewModelAuth,articles)
                }
                Indicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    isRefreshing = isLoading,
                    state = pullRefreshState
                )
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
//            CircularProgressIndicator()
//            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
//            CircularProgressIndicator(
//               color = MaterialTheme.colorScheme.primary,
//               strokeWidth = 6.dp,
//               modifier = Modifier.size(48.dp)
//           )
            AnimatedVisibility(
                visible = !check.value,
//                enter = fadeIn() + slideInVertically(initialOffsetY = { it }) + slideOutVertically()
//slideOutHorizontally()
//scaleOut()
//shrinkOut(),
                exit = fadeOut(tween(durationMillis = 1000, delayMillis = 0))
            ) {
                LottieAnimation(
                    composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.amine)).value,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier
                        .size(250.dp)
                )
            }
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
