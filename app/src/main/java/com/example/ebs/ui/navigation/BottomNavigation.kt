package com.example.ebs.ui.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.ebs.ui.navigation.destinations.NavigationDestination
import com.example.ebs.utils.extractRouteName

@Composable
fun BottomNavigationItem(
    navItem: NavigationDestination<out Any>,
    selected: Boolean = false,
    navController: NavController
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
        modifier = Modifier
            .clickable {
                val currentRoute = navController.currentDestination?.route
                if (extractRouteName(currentRoute) != extractRouteName(navItem.route)) {
                    navController.navigate(navItem.route) {
                        popUpTo(
                            navController.graph.findStartDestination().id
                        ) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
    ) {
        Icon(
            painterResource(navItem.icon!!),
            contentDescription = navItem.name,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(navItem.name)
    }
}
