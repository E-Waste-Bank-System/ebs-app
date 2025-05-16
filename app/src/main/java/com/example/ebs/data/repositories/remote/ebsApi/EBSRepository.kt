package com.example.ebs.data.repositories.remote.ebsApi

import com.example.ebs.data.structure.remote.ebs.articles.DataArticles
import com.example.ebs.data.structure.remote.ebs.detections.Detections
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
    override suspend fun uploadImage(filePath: String, token: String): Detections {
        val file = File(filePath)
        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
        return ebsApiService.postDetection(token, body)
    }
}