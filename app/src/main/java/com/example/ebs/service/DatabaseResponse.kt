package com.example.ebs.service

sealed interface DatabaseResponse{
    data object Success: DatabaseResponse
    data class Error(val message: String?): DatabaseResponse
}