package com.example.ebs.ui.navigation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.ebs.data.structure.remote.ebs.articles.Article
import com.example.ebs.data.structure.remote.ebs.detections.head.ScanResponse
import com.example.ebs.ui.navigation.destinations.Route
import kotlinx.serialization.json.Json

class NavigationHandler(private val navController: NavController) {
    private fun navigateWithPopUpTo(
        route: Any,
        popUpToRoute: Any,
        inclusive: Boolean = true,
        launchSingleTop: Boolean = true
    ) {
        navController.navigate(route) {
            popUpTo(popUpToRoute) {
                this.inclusive = inclusive
            }
            this.launchSingleTop = launchSingleTop
        }
    }

    private fun justNavigate(route: Any) {
        navController.navigate(route){
            popUpTo(navController.graph.findStartDestination().id)
            launchSingleTop = true
        }
    }

    private fun navBarNavigate(route: Any) {
        navController.navigate(route) {
            popUpTo(
                navController.graph.findStartDestination().id
            ) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    private fun dialogueNav(route: Any) {
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
    val signUpFromSignIn: () -> Unit = { navigateWithPopUpTo(Route.SignUp, Route.SignIn) }
    val menuFromSignIn: () -> Unit = { navigateWithPopUpTo(Route.Dashboard, Route.SignIn) }
    val menuFromSignUp: () -> Unit = { navigateWithPopUpTo(Route.Dashboard, Route.SignUp) }
    val notifikasiFromMenu: () -> Unit = { justNavigate(Route.Notifikasi) }
    fun detailFromMenu (data: ScanResponse, img: String, fromScan: Boolean = false) {
        val json = Json.encodeToString(ScanResponse.serializer(), data)
        try {
            Log.e("NavigationHandler", "detailFromMenu called with data: $data and img: $img")
            justNavigate(Route.Detail(data = json, img = img, fromScan = fromScan.toString()))
        } catch (e: Exception) {
            Log.e("NavigationHandler", "Error navigating to Detail: ${e.message}", e)
        }
    }
    fun scanFromDetail () {
        navController.navigate(Route.Scan) {
            popUpTo(
                navController.graph.findStartDestination().id
            )
            launchSingleTop = true
        }
    }
    fun articleFromMenu (article: Article) {
        val json = Json.encodeToString(Article.serializer(), article)
        try {
            justNavigate(Route.Article(data = json))
        } catch (e: Exception) {
            Log.e("NavigationHandler", "Error navigating to Detail: ${e.message}", e)
        }
    }
    val dashboard: () -> Unit = { navBarNavigate(Route.Dashboard) }
    val riwayat: () -> Unit = { navBarNavigate(Route.Riwayat) }
    val profile: () -> Unit = { justNavigate(Route.Profile) }
    val lokasi: () -> Unit = { dialogueNav(Route.Location) }
    val bantuan: () -> Unit = { dialogueNav(Route.Bantuan) }
    val beriNilai: () -> Unit = { dialogueNav(Route.BeriNilai) }
    val kontak: () -> Unit = { dialogueNav(Route.Kontak) }
    val ubah: () -> Unit = { dialogueNav(Route.Ubah) }
    val exitDialogue: () -> Unit = { justNavigate(Route.Exit) }
}