package com.example.ebs.data.repositories.remote

import kotlinx.serialization.Serializable

@Serializable
data class BookData(
    val book: Book
)