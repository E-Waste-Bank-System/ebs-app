package com.example.ebs.data.structure.remote.ebs.detections.head

import com.example.ebs.data.structure.remote.ebs.detections.Metadata
import com.example.ebs.data.structure.remote.ebs.detections.Waste
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Detection(
    val id: String = "",
    @SerialName("image_url") val imageUrl: String = "",
    val status: String = "zero",
    @SerialName("objects_count") val objectsCount: Int = 0,
    @SerialName("total_estimated_value") val totEstValue: Double = 0.0,
    @SerialName("created_at") val createdAt: Instant = Instant.fromEpochMilliseconds(0),
    @SerialName("error_message") val errorMessage: String? = "",
    @SerialName("user_id") val userId: String? = "",
    val objects: List<Waste> = emptyList(),
    val metadata: Metadata = Metadata()
)

