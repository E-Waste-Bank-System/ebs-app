package com.example.ebs.data.repositories.local

import com.example.ebs.data.structure.local.localItems.DeletedScans
import com.example.ebs.data.structure.local.localItems.ViewedArticles
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [ViewedArticles] from a given data source.
 */
interface LocalRepositoryInf {
//    /**
//     * Retrieve all the items from the the given data source.
//     */
//    fun getAllItemsStream(): Flow<List<Item>>
//
//    /**
//     * Retrieve an item from the given data source that matches with the [id].
//     */
//    fun getItemStream(id: Int): Flow<Item?>
//
//    /**
//     * Insert item in the data source
//     */
//    suspend fun insertItem(item: Item)
//
//    /**
//     * Delete item from the data source
//     */
//    suspend fun deleteItem(item: Item)
//
//    /**
//     * Update item in the data source
//     */
//    suspend fun updateItem(item: Item)
     suspend fun insertViewedArticle(article: ViewedArticles)

     fun getAllViewedArticles(): Flow<List<ViewedArticles>>

     suspend fun insertDeletedScan(scan: DeletedScans)

     fun getAllDeletedScans(): Flow<List<DeletedScans>>
}