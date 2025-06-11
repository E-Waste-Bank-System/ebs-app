package com.example.ebs.data.structure.remote.ebs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TesLogin(
    @SerialName("user_id") val userId: String,
    val email: String,
    val password: String,
)
