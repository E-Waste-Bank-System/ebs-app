package com.example.ebs.data.structure.remote.book

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val id: String,
    val name: String,
    val year: Int,
    val author: String,
    val summary: String,
    val publisher: String,
    val pageCount: Int,
    val readPage: Int,
    val finished: Boolean,
    val reading: Boolean,
    val insertedAt: String,
    val updatedAt: String
    //    @SerialName("img_src") val imgSrc: String
)