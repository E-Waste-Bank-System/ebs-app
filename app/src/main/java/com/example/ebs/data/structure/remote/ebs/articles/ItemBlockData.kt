package com.example.ebs.data.structure.remote.ebs.articles

import kotlinx.serialization.Serializable

@Serializable
data class ItemBlockData(
    val meta: MetaBlockData = MetaBlockData(),
    val items: List<String> = emptyList(),
    val content: String = "",
)