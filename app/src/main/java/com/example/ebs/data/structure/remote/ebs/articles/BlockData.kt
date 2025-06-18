package com.example.ebs.data.structure.remote.ebs.articles

import kotlinx.serialization.Serializable

@Serializable
data class BlockData(
    val text: String = "",
    val style: String = "",
    val items: List<ItemBlockData> = emptyList(),
    val caption: String = "",
    val alignment: String = "",
    val code: String = "",
    val content: List<List<String>> = emptyList()
)

