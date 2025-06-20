package com.example.ebs.data.repositories.remote.ebsApi

import com.example.ebs.data.structure.remote.ebs.auth.UserMintaToken
import com.example.ebs.data.structure.remote.ebs.detections.head.Detection
import com.example.ebs.data.structure.remote.ebs.detections.head.Histories
import com.example.ebs.data.structure.remote.ebs.detections.head.ScanResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class EBSRepository(
    private val ebsApiService: EBSApiService
) : EBSRepositoryInf {
//    override suspend fun getData(token: String): DataArticles {
//        return ebsApiService.getArticles("Bearer $token")
//    }

//    override suspend fun loginAdmin(userId: String, email: String, password: String): String {
//        val loginRequest = AdminTesLogin(userId, email, password)
//        val response = ebsApiService.postLoginReq(loginRequest)
//        return response.token
//    }

    override suspend fun loginUser(userId: String): String {
        val loginRequest =
            UserMintaToken(userId)
        val response =
            ebsApiService.getUserToken(loginRequest)
        return response.token
    }

    override suspend fun uploadImage(
        userId: String, filePath: String, token: String
    ): ScanResponse {
     val file = File(filePath)
     val requestFile =
         file.asRequestBody("image/png".toMediaTypeOrNull())
     val imagePart =
         MultipartBody.Part.createFormData(
             "file", file.name, requestFile
         )
     return ebsApiService.postDetection("Bearer $token", imagePart)
    }
    override suspend fun getHistory(token: String): Histories {
        return ebsApiService.getUserDetections("Bearer $token")
    }

    override suspend fun getDetection(token: String, userId: String): Detection {
        return ebsApiService.getDetection("Bearer $token", userId)
    }
}