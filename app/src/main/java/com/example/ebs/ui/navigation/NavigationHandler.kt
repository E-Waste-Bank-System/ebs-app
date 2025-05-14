package com.example.ebs.ui.navigation

import androidx.navigation.NavController
import com.example.ebs.ui.navigation.destinations.Route

class NavigationHandler(private val navController: NavController) {
    private fun navigateWithPopUpTo(
        route: Any,
        popUpToRoute: Any,
        inclusive: Boolean = true,
        saveState: Boolean = true,
        launchSingleTop: Boolean = true,
        restoreState: Boolean = true
    ) {
        navController.navigate(route) {
            popUpTo(popUpToRoute) {
                this.inclusive = inclusive
                this.saveState = saveState
            }
            this.launchSingleTop = launchSingleTop
            this.restoreState = restoreState
        }
    }
    private fun justNavigate(
        route: Any
    ) {
        navController.navigate(route)
    }
    fun back() {
        navController.popBackStack()
    }
    fun closeApp() {
        val activity = navController.context as? android.app.Activity
        activity?.finish()
    }
    val welcomeFromMenu: () -> Unit = { navigateWithPopUpTo(Route.Welcome, Route.Dashboard) }
    val signInFromWelcome: () -> Unit = { navigateWithPopUpTo(Route.SignIn, Route.Welcome) }
    val signInFromSignUp: () -> Unit = { navigateWithPopUpTo(Route.SignIn, Route.SignUp) }
    val signUpFromWelcome: () -> Unit = { navigateWithPopUpTo(Route.SignUp, Route.Welcome) }
    val signUpFromSignIn: () -> Unit = { navigateWithPopUpTo(Route.SignUp, Route.SignIn) }
    val menuFromSignIn: () -> Unit = { navigateWithPopUpTo(Route.Dashboard, Route.SignIn) }
    val menuFromSignUp: () -> Unit = { navigateWithPopUpTo(Route.Dashboard, Route.SignUp) }
    val notifikasiFromMenu: () -> Unit = { navigateWithPopUpTo(Route.Notifikasi, Route.Dashboard) }
    val dialogueSetting: () -> Unit = { justNavigate(Route.Settings) }
    val exitDialogue: () -> Unit = { justNavigate(Route.Exit) }
}