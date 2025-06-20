package com.example.ebs.ui.screens.dashboard

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.ebs.R
import com.example.ebs.data.repositories.UserPreferencesRepository
import com.example.ebs.data.structure.remote.ebs.detections.head.ScanResponse
import com.example.ebs.service.EBSNotificationService
import com.example.ebs.ui.components.gradients.getGredienBackground
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.navigation.BotBarPage
import com.example.ebs.ui.screens.MainViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    userPref: UserPreferencesRepository,
    viewModelMain: MainViewModel,
    viewModel: DashboardViewModel = hiltViewModel(),
    //    viewModel: DashboardViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val postNotificationPermission =
        rememberPermissionState(
            permission = Manifest.permission.POST_NOTIFICATIONS
        )
    val context = LocalContext.current
    val eBSNotificationService = EBSNotificationService(context)

    val loadStatus = rememberSaveable { mutableStateOf(false) }
    val check = remember { mutableStateOf(false) }

    val scanResult by viewModelMain.scanResult.collectAsState()
    LaunchedEffect(scanResult) {
        if ((scanResult?.split(" ")?.first() ?: "") == "scan_result") {
            viewModelMain.refresh()
            val result = viewModelMain.pollResultOnce(
                viewModelMain.scanResult.value?.split(" ")?.get(1) ?: ""
            )
            viewModelMain.navHandler.detailFromMenu(
                ScanResponse(
                    id = result.id,
                    status = result.status,
                    createdAt = result.createdAt,
                ),
                result.imageUrl
            )
            viewModelMain.updateScanResult(null)
        }

    }

    LaunchedEffect(Unit) {
        if (viewModelMain.firstOpen) {
            viewModelMain.firstOpen = false
            coroutineScope {
                val refreshJob = async { viewModelMain.refresh() }
                val userDataJob = async { viewModelMain.getUserData() }
                val localDataJob = async {
                    if(userPref.name.first() != null && userPref.name.first() != "") {
                        viewModelMain.updateUserInfo(
                            viewModelMain.localInfo.copy(
                                name = userPref.name.first(),
                            )
                        )
                    }
                }
                val delayJob = async { delay(2000) }
                val checkJob = async { viewModelMain.authManagerState.isSignedIn() }
                refreshJob.await()
                userDataJob.await()
                localDataJob.await()
                delayJob.await()
                check.value = checkJob.await()
                delay(1000)
            }
            loadStatus.value = true
        } else {
            loadStatus.value = true
        }
    }

    Log.e("Route", "This is Dashboard first? ${viewModelMain.firstOpen}")
    if (loadStatus.value && !viewModelMain.firstOpen) {
        if(viewModelMain.authManagerState.checkVerification() == null){
            LaunchedEffect(Unit) {
                if (postNotificationPermission.status.isGranted) {
                    eBSNotificationService.showUnverifiedNotification()
                } else {
                    postNotificationPermission.launchPermissionRequest()
                }
            }
        }

        val exitDialogue = remember { mutableStateOf(false) }

        BackHandler { exitDialogue.value = true }

        if (exitDialogue.value) {
            viewModelMain.navHandler.exitDialogue()
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
            hazeState = viewModelMain.hazeState
        ) {
            val history by viewModelMain.history.collectAsState()
            val articles by viewModelMain.articles.collectAsState()
            val isLoading by viewModelMain.isLoading.collectAsState()
            val pullRefreshState = rememberPullToRefreshState()
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
                CenterColumn(
                    modifier = Modifier
                        .scrollable(
                            state = rememberScrollableState { 0f },
                            orientation = Orientation.Vertical
                        )
                ) {
                    Greeting(viewModelMain)
                    Trending(viewModelMain,history)
                    Sorotan(viewModelMain,articles)
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
                    composition = rememberLottieComposition(
                        LottieCompositionSpec
                            .RawRes(R.raw.amine)
                    ).value,
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
