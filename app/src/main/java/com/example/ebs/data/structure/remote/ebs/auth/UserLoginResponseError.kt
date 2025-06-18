package com.example.ebs.data.structure.remote.ebs.auth

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginResponseError(
    val statusCode: String = "",
    val message: List<String> = listOf(""),
    val error: String = "",
    val timestamp: String = "",
    val path: String = ""
)