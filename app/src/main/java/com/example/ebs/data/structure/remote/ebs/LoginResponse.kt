package com.example.ebs.data.structure.remote.ebs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val user: User,
    val token: String
)
@Serializable
data class User(
    val id: String,
    val email: String,
    @SerialName("is_admin") val isAdmin: Boolean
)