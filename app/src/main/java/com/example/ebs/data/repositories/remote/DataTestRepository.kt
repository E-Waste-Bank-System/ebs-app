package com.example.ebs.data.repositories.remote

import kotlinx.coroutines.flow.Flow

/**
 * Repository retrieves amphibian data from underlying data source.
 */
interface DataTestRepository {
    /** Retrieves list of amphibians from underlying data source */
    suspend fun getData(): Flow<DataTest>
    suspend fun uploadImage(filePath: String, token: String): Detections
}