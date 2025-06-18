package com.example.ebs.data.structure.remote.ebs.detections

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Metadata(
    @SerialName("ai_service_response") val aiSerResp: AiServiceResponse = AiServiceResponse(),
    @SerialName("processing_completed_at") val prosCompAt: Instant = Instant.fromEpochMilliseconds(0),
)