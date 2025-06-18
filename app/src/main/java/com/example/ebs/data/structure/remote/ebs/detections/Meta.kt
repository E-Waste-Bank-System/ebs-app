package com.example.ebs.data.structure.remote.ebs.detections

import kotlinx.serialization.Serializable

@Serializable
data class Meta (
    val page: Int = 1,
    val limit: Int = 20,
    val total: Int = 0,
    val pages: Int = 1
)