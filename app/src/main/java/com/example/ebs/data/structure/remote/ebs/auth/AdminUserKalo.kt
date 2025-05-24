package com.example.ebs.data.structure.remote.ebs.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AdminUserKalo(
    val id: String = "",
    val email: String = "",
    @SerialName("is_admin") val isAdmin: Boolean = false
)

