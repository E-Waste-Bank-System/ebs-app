package com.example.ebs.data.repositories.remote.ebsApi

import com.example.ebs.data.structure.remote.ebs.articles.DataArticles
import com.example.ebs.data.structure.remote.ebs.auth.AdminLoginResponse
import com.example.ebs.data.structure.remote.ebs.auth.AdminTesLogin
import com.example.ebs.data.structure.remote.ebs.auth.UserLoginResponse
import com.example.ebs.data.structure.remote.ebs.auth.UserMintaToken
import com.example.ebs.data.structure.remote.ebs.detections.DataDetections
import com.example.ebs.data.structure.remote.ebs.detections.Histories
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface EBSApiService {
    @GET("articles")
    suspend fun getData(
        @Header("Authorization") token: String
    ): DataArticles

    @POST("auth/login")
    suspend fun tesLoginAdmin(
        @Body loginRequest: AdminTesLogin
    ): AdminLoginResponse

    @POST("auth/token")
    suspend fun tesLoginUser(
        @Body loginRequest: UserMintaToken
    ): UserLoginResponse

    @Multipart
    @Headers("Accept: application/json")
    @POST("detections")
    suspend fun postDetection(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part,
        @Part userId: MultipartBody.Part
    ): DataDetections

    @GET("detections/user/{userId}")
    suspend fun getUserDetections(
        @Header("Authorization") token: String,
        @retrofit2.http.Path("userId") userId: String
    ): List<Histories>

    companion object {
        const val POLLING_INTERVAL = 5000L // 5 seconds
    }
}