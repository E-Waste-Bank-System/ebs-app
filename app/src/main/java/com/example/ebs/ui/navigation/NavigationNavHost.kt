package com.example.ebs.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.ebs.ui.navigation.destinations.Route

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MyNavigationg(
    signedIn: MutableState<String?>,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    Surface {
        NavHost(
            navController = navController,
            startDestination = if(signedIn.value != null) Route.Welcome else (Route.Dashboard),
            modifier = modifier
        ) {
            val navHandler = NavigationHandler(navController)
            mainNav(navController, signedIn, navHandler)
        }
    }
}




