package com.example.ebs.ui.face.starter

import androidx.lifecycle.ViewModel
import com.example.ebs.service.AuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authManager: AuthManager
) : ViewModel() {
    val authManagerState: AuthManager
        get() = authManager
}