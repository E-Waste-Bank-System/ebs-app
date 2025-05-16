package com.example.ebs.data.structure.remote.ebs.detections

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Detections(
    val id: String,
    @SerialName("user_id") val userId: String,
    @SerialName("image_url")val imageUrl: String,
    val label: String,
    val confidence: Int,
    @SerialName("regression_result")val regressionResult: Int,
    @SerialName("created_at")val createdAt: String
)