package com.example.ebs.data.structure.remote.ebs.auth

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginResponse(
    val user: User = User(),
    val token: String = ""
)