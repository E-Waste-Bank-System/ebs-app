package com.example.ebs.data.structure.remote.ebs.detections

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Histories (
    val id: String = "",
    @SerialName("user_id") val userId: String = "",
    val status: String = "",
    val objects: List<DetectionHistory> = listOf(DetectionHistory()),
    @SerialName("created_at") val createdAt: Instant = Instant.parse("2023-10-01T00:00:00Z"),
    @SerialName("updated_at") val updatedAt: Instant = Instant.parse("2023-10-01T00:00:00Z")
)