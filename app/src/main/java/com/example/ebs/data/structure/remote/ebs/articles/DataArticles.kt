package com.example.ebs.data.structure.remote.ebs.articles

import kotlinx.serialization.Serializable

@Serializable
data class DataArticles (
    val data: List<Articles>,
    val total: Int
)