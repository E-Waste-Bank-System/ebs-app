package com.example.ebs.data.structure.remote.ebs.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserLoginResponse(
    @SerialName("access_token") val token: String = "",
    val user: User = User()
)

