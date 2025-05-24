package com.example.ebs.data.structure.remote.ebs.detections

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataDetections (
    val message: String = "",
    @SerialName("scan_id") val scanId: String = "",
    val predictions: List<Detection> = listOf(Detection())
)