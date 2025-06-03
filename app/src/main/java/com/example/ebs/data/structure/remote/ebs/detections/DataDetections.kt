package com.example.ebs.data.structure.remote.ebs.detections

import kotlinx.serialization.Serializable

@Serializable
data class DataDetections (
    val message: String = "",
    val data: DataNewDetection = DataNewDetection()
)