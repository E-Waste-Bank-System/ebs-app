package com.example.ebs.ui.screens.dashboard

import android.Manifest
import android.os.Build
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.ebs.service.UpdateService
import com.example.ebs.ui.components.gradients.getGredienBackground
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.dialogues.UpdateAvailable
import com.example.ebs.ui.navigation.BotBarPage
import com.example.ebs.ui.screens.MainViewModel
import com.example.ebs.ui.screens.dashboard.components.Greeting
import com.example.ebs.ui.screens.dashboard.components.Sorotan
import com.example.ebs.ui.screens.dashboard.components.Trending
import com.example.ebs.utils.CURRENT_VERSION
import com.example.ebs.utils.MAX_VERSION
import com.example.ebs.utils.compareVersions
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

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
    val updateService = UpdateService(context)
    val eBSNotificationService = EBSNotificationService(context)

    val coroutineScope = rememberCoroutineScope()

    val checkUpdate = remember { mutableStateOf("") }
    val loadDone = remember { mutableStateOf(false) }
    val updateReminder = remember { mutableStateOf(false) }
    val loadStatus = rememberSaveable { mutableStateOf(false) }

    val scanResultNotif by viewModelMain.scanResult.collectAsState()

    LaunchedEffect(scanResultNotif) {
        try {
            if ((scanResultNotif?.split(" ")?.first() ?: "") == "scan_result") {
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
                viewModelMain.refresh()
            }
        } catch (e: Exception) {
            Toast.makeText(
                context,
                if (e.localizedMessage?.contains("Unable to resolve host", ignoreCase = true) == true)
                    "Ups?! Tidak ada koneksi internet"
                else
                    e.localizedMessage,
                Toast.LENGTH_SHORT
            ).show()
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
                val delayJob = async { delay(500) }
                val checkJob = async { viewModelMain.authManagerState.isSignedIn() }
                val checkUpdateJob = withContext(Dispatchers.IO) {
                    async {
                        if(true) {
                            updateService.getLatestNightlyUrl(MAX_VERSION)
                        }
                        else
                            ""
                    }
                }
                refreshJob.await()
                userDataJob.await()
                localDataJob.await()
                delayJob.await()
                checkUpdate.value = checkUpdateJob.await()
                loadDone.value = checkJob.await()
                delay(500)
            }

            val regex = """/download/v([^/]+)/""".toRegex()
            val version = regex.find(checkUpdate.value)?.groupValues?.get(1)
            if (version == null) {
                Log.e(
                    "UpdateService",
                    "No update available or already" +
                            " on latest version $version - ${checkUpdate.value}"
                )
            } else if (compareVersions(version, CURRENT_VERSION) > 0) {
                Log.e(
                    "UpdateService",
                    "Update available or already" +
                            " on latest version $version - ${checkUpdate.value}"
                )
                updateReminder.value = true
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
                visible = !loadDone.value,
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
    if (updateReminder.value) {
        UpdateAvailable(MAX_VERSION,updateReminder, coroutineScope, updateService, viewModelMain)
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
