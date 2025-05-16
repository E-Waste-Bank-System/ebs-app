package com.example.ebs.data.repositories.remote.book

import com.example.ebs.data.structure.remote.book.DataTest
import retrofit2.http.GET

interface BookApiService {
    @GET("books/cPCFaGiIXO_KK6wR")
    suspend fun getData(): DataTest

    companion object {
        const val POLLING_INTERVAL = 5000L // 5 seconds
    }
}

