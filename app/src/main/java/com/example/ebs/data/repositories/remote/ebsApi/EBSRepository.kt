package com.example.ebs.data.repositories.remote.ebsApi

import android.util.Log
import com.example.ebs.data.structure.remote.ebs.articles.DataArticles
import com.example.ebs.data.structure.remote.ebs.auth.AdminTesLogin
import com.example.ebs.data.structure.remote.ebs.auth.UserMintaToken
import com.example.ebs.data.structure.remote.ebs.detections.DataDetections
import com.example.ebs.data.structure.remote.ebs.detections.Histories
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class EBSRepository(
    private val ebsApiService: EBSApiService
) : EBSRepositoryInf {
    override suspend fun getData(token: String): DataArticles {
        return ebsApiService.getData("Bearer $token")
    }

    override suspend fun loginAdmin(userId: String, email: String, password: String): String {
        val loginRequest = AdminTesLogin(userId, email, password)
        val response = ebsApiService.tesLoginAdmin(loginRequest)
        return response.token
    }

    override suspend fun loginUser(userId: String): String {
        val loginRequest = UserMintaToken(userId)
        val response = ebsApiService.tesLoginUser(loginRequest)
        return response.token
    }

    override suspend fun uploadImage(userId: String, filePath: String, token: String): DataDetections {
     val file = File(filePath)
     val requestFile = file.asRequestBody("image/png".toMediaTypeOrNull())
     val imagePart = MultipartBody.Part.createFormData("file", file.name, requestFile)
     val userIdPart = MultipartBody.Part.createFormData("user_id", userId)
     Log.e("its", "ITS IN")
     return ebsApiService.postDetection("Bearer $token", imagePart, userIdPart)
    }
    override suspend fun getHistory(token: String, userId: String): List<Histories> {
        return ebsApiService.getUserDetections("Bearer $token", userId)
    }
}