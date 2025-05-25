package com.example.ebs.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.example.ebs.data.repositories.UserPreferencesRepository
import com.example.ebs.data.structure.remote.ebs.articles.Article
import com.example.ebs.data.structure.remote.ebs.detections.DataDetections
import com.example.ebs.ui.dialogues.ApplyRequest
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
    viewModelAuth: MainViewModel,
    navigateTo: String?,
){
    composable<Route.Welcome>{
        WelcomeScreen(navController,viewModelAuth)
    }
    composable<Route.SignUp> {
        SignUpScreen(navController,userPref,viewModelAuth)
    }
    composable<Route.SignIn> {
        SignInScreen(navController,userPref,viewModelAuth,navigateTo)
    }
    composable<Route.Detail> { backStackEntry ->
        val data: DataDetections = backStackEntry.arguments?.getString("data")?.let {
           Json.decodeFromString<DataDetections>(it)
        } ?: error("Detection data missing")
        WasteDetailScreen(data,viewModelAuth)
    }
    composable<Route.Article> { backStackEntry ->
        val data: Article = backStackEntry.arguments?.getString("data")?.let {
            Json.decodeFromString<Article>(it)
        } ?: error("Article data missing")
        ArticleScreen(navController,data,viewModelAuth)
    }
    composable<Route.Notifikasi> {
        NotifikasiScreen(navController,viewModelAuth)
    }
    composable<Route.Dashboard> {
        DashboardScreen(navController, userPref,viewModelAuth)
    }
    composable<Route.Riwayat> {
        DetectionListScreen(navController,viewModelAuth)
    }
    composable<Route.Scan> {
        ScanScreen(navController,viewModelAuth)
    }
    composable<Route.Profile> {
        ProfileScreen(navController, userPref,viewModelAuth)
    }
    dialog<Route.Location> {
        Lokasi()
    }
    dialog<Route.Bantuan> {
        Bantuan()
    }
    dialog<Route.BeriNilai> {
        BeriNilai()
    }
    dialog<Route.Kontak> {
        Kontak()
    }
    dialog<Route.Ubah> {
        Ubah(viewModelAuth,userPref)
    }
    dialog<Route.Settings> {
        ApplyRequest()
    }
    dialog<Route.Exit> {
        Exit(navController,viewModelAuth)
    }
}
