package com.example.ebs.ui.screens.starter

import androidx.lifecycle.ViewModel
import com.example.ebs.data.repositories.local.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : ViewModel() {
}