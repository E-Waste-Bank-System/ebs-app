package com.example.ebs.ui.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ebs.ui.face.components.structures.CenterColumn
import com.example.ebs.ui.navigation.destinations.NavigationDestinationList
import com.example.ebs.utils.extractRouteName
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BotBarPage(
    navController: NavController,
    signedIn: MutableState<String?>,
    modifier: Modifier = Modifier,
    hazeState: HazeState = remember { HazeState() },
    content: @Composable () -> Unit
){
    Scaffold(
        bottomBar = {
            if (signedIn.value != null) {
                BottomNavigation(
                    hazeState = hazeState
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    NavigationDestinationList.forEach { navItem ->
                        if (navItem.icon != null) {
                            BottomNavigationItem(
                                navItem,
                                selected = currentDestination
                                    ?.hierarchy
                                    ?.any {
                                        extractRouteName(navItem.route) == extractRouteName(it.route)
                                    } == true,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    ) {
        CenterColumn(
            vArr = Arrangement.Top,
            modifier = modifier
                .fillMaxSize()
                .hazeSource(hazeState)
        ) {
            content()
        }
    }
}