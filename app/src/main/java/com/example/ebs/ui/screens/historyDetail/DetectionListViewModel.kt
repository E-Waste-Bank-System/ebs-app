package com.example.ebs.ui.screens.historyDetail

import androidx.lifecycle.ViewModel
import com.example.ebs.data.repositories.remote.ebsApi.EBSRepository
import com.example.ebs.service.auth.AuthManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DetectionListViewModel @Inject constructor(
    private val ebsRepository: EBSRepository,
    private val authManager: AuthManager
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
}
