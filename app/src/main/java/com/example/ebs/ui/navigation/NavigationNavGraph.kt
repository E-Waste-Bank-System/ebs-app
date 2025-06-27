package com.example.ebs.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.example.ebs.data.repositories.UserPreferencesRepository
import com.example.ebs.data.structure.remote.ebs.articles.Article
import com.example.ebs.data.structure.remote.ebs.detections.head.ScanResponse
import com.example.ebs.ui.dialogues.bases.ApplyRequest
import com.example.ebs.ui.dialogues.Bantuan
import com.example.ebs.ui.dialogues.BeriNilai
import com.example.ebs.ui.dialogues.Exit
import com.example.ebs.ui.dialogues.Kontak
import com.example.ebs.ui.dialogues.Lokasi
import com.example.ebs.ui.dialogues.Ubah
import com.example.ebs.ui.navigation.destinations.Route
import com.example.ebs.ui.screens.MainViewModel
import com.example.ebs.ui.screens.article.ArticleScreen
import com.example.ebs.ui.screens.dashboard.DashboardScreen
import com.example.ebs.ui.screens.detail.WasteDetailScreen
import com.example.ebs.ui.screens.historyDetail.DetectionListScreen
import com.example.ebs.ui.screens.notification.NotifikasiScreen
import com.example.ebs.ui.screens.profile.ProfileScreen
import com.example.ebs.ui.screens.scan.ScanScreen
import com.example.ebs.ui.screens.starter.SignInScreen
import com.example.ebs.ui.screens.starter.SignUpScreen
import com.example.ebs.ui.screens.starter.WelcomeScreen
import kotlinx.serialization.json.Json

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun NavGraphBuilder.mainNav(
    navController: NavController,
    userPref: UserPreferencesRepository,
    viewModelMain: MainViewModel,
){
    composable<Route.Welcome>{
        WelcomeScreen(viewModelMain)
    }
    composable<Route.SignUp> {
        SignUpScreen(viewModelMain)
    }
    composable<Route.SignIn> {
        SignInScreen(viewModelMain)
    }
    composable<Route.Detail> { backStackEntry ->
        val data: ScanResponse =
            backStackEntry.arguments?.getString("data")?.let {
           Json.decodeFromString<ScanResponse>(it)
        } ?: error("Detection data missing")
        val img: String? =
            backStackEntry.arguments?.getString("img")
        val fromScan: String? =
            backStackEntry.arguments?.getString("fromScan")
        WasteDetailScreen(data,img,viewModelMain,fromScan.toBoolean())
    }
    composable<Route.Article> { backStackEntry ->
        val data: Article =
            backStackEntry.arguments?.getString("data")?.let {
            Json.decodeFromString<Article>(it)
        } ?: error("Article data missing")
        ArticleScreen(data, viewModelMain)
    }
    composable<Route.Notifikasi> {
        NotifikasiScreen(viewModelMain)
    }
    composable<Route.Dashboard> {
        DashboardScreen(navController, userPref,viewModelMain)
    }
    composable<Route.Riwayat> {
        DetectionListScreen(navController,viewModelMain)
    }
    composable<Route.Scan> {
        ScanScreen(viewModelMain)
    }
    composable<Route.Profile> {
        ProfileScreen(navController, userPref,viewModelMain)
    }
    dialog<Route.Location> {
        Lokasi(navController)
    }
    dialog<Route.Bantuan> {
        Bantuan(navController)
    }
    dialog<Route.BeriNilai> {
        BeriNilai(navController)
    }
    dialog<Route.Kontak> {
        Kontak(navController)
    }
    dialog<Route.Ubah> {
        Ubah(viewModelMain,userPref)
    }
    dialog<Route.Settings> {
        ApplyRequest(navController)
    }
    dialog<Route.Exit> {
        Exit(viewModelMain)
    }
}
