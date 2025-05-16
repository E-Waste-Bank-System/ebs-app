package com.example.ebs.data.repositories.remote.ebsApi

import com.example.ebs.data.structure.remote.ebs.articles.DataArticles
import com.example.ebs.data.structure.remote.ebs.detections.Detections
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface EBSApiService {
    @GET("articles")
    suspend fun getData(
        @Header("Authorization") token: String
    ): DataArticles

    @Multipart
    @POST("detections")
    suspend fun postDetection(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    ): Detections

    companion object {
        const val POLLING_INTERVAL = 5000L // 5 seconds
    }
}