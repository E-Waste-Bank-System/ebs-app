package com.example.ebs.data.structure.remote.ebs.articles

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    @SerialName("image_url") val imageUrl: String = "",
    @SerialName("created_at") val createdAt: Instant = Instant.parse("2023-10-01T00:00:00Z")
)