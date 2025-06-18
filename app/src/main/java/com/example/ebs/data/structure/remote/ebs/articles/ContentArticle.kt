package com.example.ebs.data.structure.remote.ebs.articles

import kotlinx.serialization.Serializable

@Serializable
data class ContentArticle(
    val time: Long = 0L,
    val blocks: List<Block> = listOf(),
    val version: String = ""
)