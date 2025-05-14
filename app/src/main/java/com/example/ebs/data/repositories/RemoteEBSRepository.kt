package com.example.ebs.data.repositories

import com.example.ebs.data.repositories.remote.DataTest
import com.example.ebs.data.repositories.remote.DataTestRepository
import com.example.ebs.data.repositories.remote.Detections
import com.example.ebs.data.repositories.remote.EBSApiService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response

/**
 * Network Implementation of repository that retrieves amphibian data from underlying data source.
 */
class RemoteEBSRepository(
    private val ebsApiService: EBSApiService
) : DataTestRepository {
    /** Retrieves list of amphibians from underlying data source */
    override suspend fun getData(): Flow<DataTest> = flow {
        while (true) {
            emit(ebsApiService.getData())
            delay(EBSApiService.POLLING_INTERVAL)
        }
    }
    override suspend fun uploadImage(filePath: String, token: String): Detections {
        val file = java.io.File(filePath)
        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
        return ebsApiService.postDetection(token, body)
    }
}

