package com.example.ebs.data.repositories.remote

import retrofit2.http.GET

interface EBSApiService {
    @GET("books/cPCFaGiIXO_KK6wR")
    suspend fun getData(): DataTest


    companion object {
        const val POLLING_INTERVAL = 5000L // 5 seconds
    }
}