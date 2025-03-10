package com.example.ebs.service

sealed interface AuthResponse{
    data object Success: AuthResponse
    data class Error(val message: String?): AuthResponse
}