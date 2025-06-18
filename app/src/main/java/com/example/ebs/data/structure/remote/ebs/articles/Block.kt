package com.example.ebs.data.structure.remote.ebs.articles

import kotlinx.serialization.Serializable

@Serializable
data class Block(
    val id: String = "",
    val data: BlockData = BlockData(),
    val type: String = ""
)