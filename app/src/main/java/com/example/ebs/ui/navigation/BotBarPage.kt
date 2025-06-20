package com.example.ebs.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ebs.ui.components.structures.CenterColumn
import com.example.ebs.ui.navigation.destinations.MainNavbar
import com.example.ebs.utils.extractRouteName
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BotBarPage(
    navController: NavController,
    modifier: Modifier = Modifier,
    hazeState: HazeState = remember { HazeState() },
    content: @Composable () -> Unit
){
    Scaffold(
        bottomBar = {
            BottomNavigation(
                hazeState = hazeState,
                modifier = Modifier.offset(y= (20).dp)
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                MainNavbar.forEach { navItem ->
                    BottomNavigationItem(
                        navItem,
                        selected = currentDestination
                            ?.hierarchy
                            ?.any {
                                extractRouteName(
                                    navItem.route
                                ) == extractRouteName(
                                    it.route
                                )
                            } == true,
                        navController = navController
                    )
                }
            }
        },
        modifier = modifier.navigationBarsPadding()
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