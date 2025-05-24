package com.example.ebs.data.structure.remote.ebs.auth

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String = "",
    val email: String = ""
)