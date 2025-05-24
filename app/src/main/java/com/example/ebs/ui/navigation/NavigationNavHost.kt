package com.example.ebs.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.ebs.data.repositories.UserPreferencesRepository
import com.example.ebs.ui.navigation.destinations.Route
import com.example.ebs.ui.screens.MainViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MyNavigationg(
    userPref: UserPreferencesRepository,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModelAuth: MainViewModel = hiltViewModel(),
) {
    viewModelAuth.initializeNavHandler(navController)
    val holder = remember { mutableStateOf(false) }
    val loadPrevious = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        loadPrevious.value = viewModelAuth.authManagerState.isSignedIn()
        holder.value = true
    }

    if(holder.value) {
        Surface {
            NavHost(
                navController = navController,
                startDestination = if (!loadPrevious.value) Route.Welcome else (Route.Dashboard),
                modifier = modifier
            ) {
                mainNav(navController, userPref, viewModelAuth)
            }
        }
    } else {
        Surface (
            modifier = Modifier.fillMaxSize()
        ){}
    }
}




