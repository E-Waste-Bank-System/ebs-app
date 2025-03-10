package com.example.ebs.data.structure

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Requests(
    val id: String,
    @SerialName("user_id") val userId: String,
    val status: String,
    val description: String,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("created_at") val createdAt:Instant
)