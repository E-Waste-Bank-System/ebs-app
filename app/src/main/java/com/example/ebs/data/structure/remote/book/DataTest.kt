package com.example.ebs.data.structure.remote.book

import kotlinx.serialization.Serializable

/**
 * Data class that defines an amphibian which includes a name, type, description, and image URL.
 */
@Serializable
data class DataTest(
    val status: String,
    val data: BookData
)