package com.example.ebs.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.example.ebs.data.repositories.UserPreferencesRepository
import com.example.ebs.ui.screens.dashboard.DashboardScreen
import com.example.ebs.ui.screens.scan.ScanScreen
import com.example.ebs.ui.screens.detail.WasteDetailScreen
import com.example.ebs.ui.dialogues.ApplyRequest
import com.example.ebs.ui.dialogues.Exit
import com.example.ebs.ui.screens.history.DetectionListScreen
import com.example.ebs.ui.screens.notification.NotifikasiScreen
import com.example.ebs.ui.screens.profile.ProfileScreen
import com.example.ebs.ui.screens.starter.SignInScreen
import com.example.ebs.ui.screens.starter.SignUpScreen
import com.example.ebs.ui.screens.starter.WelcomeScreen
import com.example.ebs.ui.navigation.destinations.Route

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun NavGraphBuilder.mainNav(
    navController: NavController,
    userPref: UserPreferencesRepository,
){
    composable<Route.Welcome>{
        WelcomeScreen(navController)
    }
    composable<Route.SignUp> {
        SignUpScreen(navController,userPref)
    }
    composable<Route.SignIn> {
        SignInScreen(navController,userPref)
    }
    composable<Route.Detail> { backStackEntry ->
        val barang = backStackEntry.arguments?.getString("barang")
        WasteDetailScreen(navController,barang.toString())
    }
    composable<Route.Notifikasi> {
        NotifikasiScreen(navController)
    }
    composable<Route.Dashboard> {
        DashboardScreen(navController, userPref)
    }
    composable<Route.Riwayat> {
        DetectionListScreen(navController)
    }
    composable<Route.Scan> {
        ScanScreen(navController)
    }
    composable<Route.Profile> {
        ProfileScreen(navController, userPref)
    }
    dialog<Route.Settings> {
        ApplyRequest()
    }
    dialog<Route.Exit> {
        Exit(navController)
    }
}
