package com.example.ebs.data.repositories.remote.book

import com.example.ebs.data.structure.remote.book.DataTest
import kotlinx.coroutines.flow.Flow

/**
 * Repository retrieves amphibian data from underlying data source.
 */
interface
BookRepositoryInf {
    /** Retrieves list of amphibians from underlying data source */
    suspend fun getData(): Flow<DataTest>
}

