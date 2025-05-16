package com.example.ebs.data.structure.remote.ebs.articles

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Articles(
    val id: String,
    val title: String,
    val content: String,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("created_at") val createdAt: Instant
)