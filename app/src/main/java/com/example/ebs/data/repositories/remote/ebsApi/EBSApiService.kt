package com.example.ebs.data.repositories.remote.ebsApi

import com.example.ebs.data.structure.remote.ebs.auth.UserLoginResponse
import com.example.ebs.data.structure.remote.ebs.auth.UserMintaToken
import com.example.ebs.data.structure.remote.ebs.detections.head.Detection
import com.example.ebs.data.structure.remote.ebs.detections.head.Histories
import com.example.ebs.data.structure.remote.ebs.detections.head.ScanResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface EBSApiService {
//    @GET("v1/articles")
//    suspend fun getArticles(
//        @Header("Authorization") token: String
//    ): DataArticles

//    @POST("v1/auth/login")
//    suspend fun postLoginReq(
//        @Body loginRequest: AdminTesLogin
//    ): AdminLoginResponse

    @POST("v1/auth/token")
    suspend fun getUserToken(
        @Body tokenRequest: UserMintaToken
    ): UserLoginResponse

    @Multipart
    @Headers("Accept: application/json")
    @POST("v1/scans")
    suspend fun postDetection(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): ScanResponse

    @GET("v1/scans")
    suspend fun getUserDetections(
        @Header("Authorization") token: String
    ): Histories

    @GET("v1/scans/{id}")
    suspend fun getDetection(
        @Header("Authorization") token: String,
        @Path("id") userId: String
    ): Detection
}