package com.example.ebs.data.structure.remote.ebs.detections.head

import com.example.ebs.data.structure.remote.ebs.detections.Meta
import kotlinx.serialization.Serializable

@Serializable
data class Histories (
    val data: List<ScanResponse> = emptyList(),
    val meta: Meta = Meta()
)

