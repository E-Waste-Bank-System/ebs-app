package com.example.ebs.data.repositories.remote

import okhttp3.MultipartBody
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface EBSApiService {
    @GET("books/cPCFaGiIXO_KK6wR")
    suspend fun getData(): DataTest

    @Multipart
    @POST("api/detections")
    suspend fun postDetection(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    ): Detections

    companion object {
        const val POLLING_INTERVAL = 5000L // 5 seconds
    }
}