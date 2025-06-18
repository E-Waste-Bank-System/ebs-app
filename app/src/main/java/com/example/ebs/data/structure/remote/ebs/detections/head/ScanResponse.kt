package com.example.ebs.data.structure.remote.ebs.detections.head

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScanResponse (
    val id: String = "",
    @SerialName("image_url") val imgUrl: String = "",
    val status: String = "",
    @SerialName("objects_count") val objectsCount: Int = 0,
    @SerialName("total_estimated_value") val totEstValue: Double = 0.0,
    @SerialName("created_at") val createdAt: Instant = Instant.fromEpochMilliseconds(0),
    @SerialName("error_message") val errorMessage: String? = ""
)

