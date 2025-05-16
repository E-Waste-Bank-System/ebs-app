package com.example.ebs.service.database

sealed interface DatabaseResponse{
    data object Success: DatabaseResponse
    data class Error(val message: String?): DatabaseResponse
}