package com.example.ebs.data.repositories.remote.ebsApi

import com.example.ebs.data.structure.remote.ebs.detections.head.Detection
import com.example.ebs.data.structure.remote.ebs.detections.head.Histories
import com.example.ebs.data.structure.remote.ebs.detections.head.ScanResponse

interface EBSRepositoryInf {
//    suspend fun getData(token: String): DataArticles
//    suspend fun loginAdmin(userId: String, email: String, password: String): String
    suspend fun loginUser(userId: String): String
    suspend fun uploadImage(userId: String, filePath: String, token: String): ScanResponse
    suspend fun getHistory(token: String): Histories
    suspend fun getDetection(token: String, userId: String): Detection
}