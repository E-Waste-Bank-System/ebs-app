package com.example.ebs.data.structure.remote.ebs.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String = "",
    val email: String = "",
    @SerialName("full_name") val fullName: String = "",
    val role: String = "",
    @SerialName("avatar_url") val imgUrl: String? = ""
)