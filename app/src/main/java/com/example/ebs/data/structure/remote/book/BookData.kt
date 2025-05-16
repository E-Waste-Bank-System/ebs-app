package com.example.ebs.data.structure.remote.book

import kotlinx.serialization.Serializable

@Serializable
data class BookData(
    val book: Book
)