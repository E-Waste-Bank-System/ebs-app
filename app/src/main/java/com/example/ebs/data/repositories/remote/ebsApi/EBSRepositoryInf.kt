package com.example.ebs.data.repositories.remote.ebsApi

import com.example.ebs.data.structure.remote.ebs.articles.DataArticles
import com.example.ebs.data.structure.remote.ebs.detections.Detections

interface EBSRepositoryInf {
    suspend fun getData(token: String): DataArticles
    suspend fun uploadImage(filePath: String, token: String): Detections
}