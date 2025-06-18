package com.example.ebs.data.structure.remote.ebs.detections

import kotlinx.serialization.Serializable

@Serializable
data class AiServiceResponse(
    val predictions: List<Prediction> = emptyList()
)