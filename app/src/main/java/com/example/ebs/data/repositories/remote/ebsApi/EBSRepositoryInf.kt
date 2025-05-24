package com.example.ebs.data.repositories.remote.ebsApi

import com.example.ebs.data.structure.remote.ebs.articles.DataArticles
import com.example.ebs.data.structure.remote.ebs.detections.DataDetections
import com.example.ebs.data.structure.remote.ebs.detections.Histories

interface EBSRepositoryInf {
    suspend fun getData(token: String): DataArticles
    suspend fun loginAdmin(userId: String, email: String, password: String): String
    suspend fun loginUser(userId: String): String
    suspend fun uploadImage(userId: String, filePath: String, token: String): DataDetections
    suspend fun getHistory(token: String, userId: String): List<Histories>
}