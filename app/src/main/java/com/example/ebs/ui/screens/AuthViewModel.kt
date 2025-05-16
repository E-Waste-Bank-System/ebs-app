package com.example.ebs.ui.screens

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.ebs.service.auth.AuthManager
import com.example.ebs.ui.navigation.NavigationHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authManager: AuthManager
) : ViewModel() {
    val authManagerState: AuthManager
        get() = authManager

    lateinit var navHandler: NavigationHandler
    lateinit var localCred: String

    fun initializeNavHandler(navController: NavController) {
        navHandler = NavigationHandler(navController)
    }

    fun updateLocalCred(token: String) {
        localCred = token.toString()
    }
}