package com.example.ebs.data.structure.remote.ebs.detections

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AiMetadata(
    val id: String = "",
    val source: String = "",
    @SerialName("created_by") val createdBy: String = "",
    @SerialName("detection_source") val detSource: String = "",
)