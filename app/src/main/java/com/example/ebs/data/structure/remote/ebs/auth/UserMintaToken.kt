package com.example.ebs.data.structure.remote.ebs.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserMintaToken(
    @SerialName("user_id") val userId: String = ""
)