package com.example.ebs.ui.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.ebs.ui.components.texts.TextTitleM
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
    ) {
        Icon(
            painterResource(navItem.icon!!),
            contentDescription = navItem.name,
            tint =
                if (selected)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSurfaceVariant,
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
        )
        TextTitleM( if (selected) buildAnnotatedString {
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append(navItem.name)
            }} else navItem.name,
            changePad = when (navItem.name) {
                "Home" -> TextUnit(value = 32f, type = TextUnitType.Sp)
                "Scan" -> TextUnit(value = 40f, type = TextUnitType.Sp)
                "Riwayat" -> TextUnit(value = 1f, type = TextUnitType.Sp)
                else -> TextUnit(value = 26f, type = TextUnitType.Sp)
            },
            modifier = Modifier
                .padding(
                    bottom =
                        when (navItem.name) {
                            "Home" -> 6.dp
                            "Scan" -> 0.dp
                            "Riwayat" -> 18.dp
                            else -> 12.dp
                        }
                )
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
        )
    }
}
