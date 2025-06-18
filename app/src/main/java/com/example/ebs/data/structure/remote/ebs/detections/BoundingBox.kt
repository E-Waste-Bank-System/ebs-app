package com.example.ebs.data.structure.remote.ebs.detections

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoundingBox(
    @SerialName("x") val x: Double = 0.0,
    @SerialName("y") val y: Double = 0.0,
    @SerialName("width") val width: Double = 0.0,
    @SerialName("height") val height: Double = 0.0
)