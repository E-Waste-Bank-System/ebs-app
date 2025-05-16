package com.example.ebs.data.repositories.remote.book

import com.example.ebs.data.structure.remote.book.DataTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Network Implementation of repository that retrieves amphibian data from underlying data source.
 */
class BookRepository(
    private val bookApiService: BookApiService
) : BookRepositoryInf {
    /** Retrieves list of amphibians from underlying data source */
    override suspend fun getData(): Flow<DataTest> = flow {
        while (true) {
            emit(bookApiService.getData())
            delay(BookApiService.POLLING_INTERVAL)
        }
    }
}

