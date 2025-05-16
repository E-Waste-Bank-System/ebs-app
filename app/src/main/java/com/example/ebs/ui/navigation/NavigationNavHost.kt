package com.example.ebs.ui.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import com.example.ebs.ui.screens.AuthViewModel
import com.example.ebs.ui.navigation.destinations.Route
import kotlinx.coroutines.flow.firstOrNull

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MyNavigationg(
    userPref: UserPreferencesRepository,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModelAuth: AuthViewModel = hiltViewModel(),
) {
    viewModelAuth.initializeNavHandler(navController)
    val checkIn = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val token = userPref.authToken.firstOrNull()
        viewModelAuth.updateLocalCred(token ?: "")
//        if (viewModelAuth.localCred != "") {
//            Log.e("TAG", "sign in pake token sebelumnya ya...")
//            viewModelAuth.authManagerState.signInAuthToken(viewModelAuth.localCred)
//                .collect { result ->
//                    if (result is AuthResponse.Success) {
//                        Log.e("Udah Masuk?", "Ini Udah Masuk peke tokone? ${viewModelAuth.authManagerState.isSignedIn()}")
//                        viewModelAuth.navHandler.menuFromSignIn()
//                    } else {
//                        Log.d("AuthManager", result.toString())
//                    }
//            }
//        }
        Log.e("TAG", "FirstCredCheck: ${viewModelAuth.localCred.take(10)}")
        checkIn.value = true
    }
    if (checkIn.value) {
        Surface {
            NavHost(
                navController = navController,
                startDestination = if(viewModelAuth.authManagerState.isSignedIn() == false) Route.Welcome else (Route.Dashboard),
                modifier = modifier
            ) {
                mainNav(navController, userPref)
            }
        }
    }
}




