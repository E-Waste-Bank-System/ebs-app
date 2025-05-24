package com.example.ebs.data.structure.remote.ebs.auth

import kotlinx.serialization.Serializable


@Serializable
data class AdminLoginResponse(
    val user: AdminUserKalo = AdminUserKalo(),
    val token: String = ""
)

