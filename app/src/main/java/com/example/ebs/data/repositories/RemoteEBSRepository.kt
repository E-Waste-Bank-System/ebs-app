package com.example.ebs.data.repositories

import com.example.ebs.data.repositories.remote.DataTest
import com.example.ebs.data.repositories.remote.DataTestRepository
import com.example.ebs.data.repositories.remote.EBSApiService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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
}

