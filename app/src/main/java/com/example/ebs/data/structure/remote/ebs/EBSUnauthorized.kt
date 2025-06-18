package com.example.ebs.data.structure.remote.ebs

import kotlinx.serialization.Serializable

@Serializable
data class EBSUnauthorized (
    val statusCode : Int = 401,
    val message: String = "Unauthorized",
)