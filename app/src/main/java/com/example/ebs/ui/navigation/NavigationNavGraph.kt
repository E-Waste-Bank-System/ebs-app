package com.example.ebs.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.example.ebs.ui.face.dashboard.DashboardScreen
import com.example.ebs.ui.face.scan.ScanScreen
import com.example.ebs.ui.face.detail.WasteDetailScreen
import com.example.ebs.ui.face.dialogue.ApplyRequest
import com.example.ebs.ui.face.history.DetectionListScreen
import com.example.ebs.ui.face.notification.NotifikasiScreen
import com.example.ebs.ui.face.profile.ProfileScreen
import com.example.ebs.ui.face.starter.SignInScreen
import com.example.ebs.ui.face.starter.SignUpScreen
import com.example.ebs.ui.face.starter.WelcomeScreen
import com.example.ebs.ui.navigation.destinations.Route

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun NavGraphBuilder.mainNav(
    navController: NavController,
    signedIn: MutableState<String?>
){
    val navHandler = NavigationHandler(navController)
    composable<Route.Welcome>{
        WelcomeScreen(navHandler)
    }
    composable<Route.SignUp> {
        SignUpScreen(navHandler)
    }
    composable<Route.SignIn> {
        SignInScreen(navHandler,signedIn)
    }
    composable<Route.Detail> {
        WasteDetailScreen(navHandler)
    }
    composable<Route.Notifikasi> {
        NotifikasiScreen(navHandler)
    }
    composable<Route.Dashboard> {
        DashboardScreen(navController,signedIn,navHandler)
    }
    composable<Route.Riwayat> {
        DetectionListScreen(navController,signedIn,navHandler)
    }
    composable<Route.Scan> {
        ScanScreen(navController,signedIn,navHandler)
    }
    composable<Route.Profile> {
        ProfileScreen(navController,signedIn,navHandler)
    }
    dialog<Route.Settings> {
        ApplyRequest()
    }
}
