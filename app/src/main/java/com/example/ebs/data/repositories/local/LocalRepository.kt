package com.example.ebs.data.repositories.local

import com.example.ebs.data.structure.local.localItems.DeletedScans
import com.example.ebs.data.structure.local.localItems.ViewedArticles
import kotlinx.coroutines.flow.Flow

class LocalRepository(private val localDao: LocalDao) : LocalRepositoryInf {
//    override fun getAllItemsStream(): Flow<List<Item>> = viewedArticlesDao.getAllItems()
//
//    override fun getItemStream(id: Int): Flow<Item?> = viewedArticlesDao.getItem(id)
//
//    override suspend fun insertItem(item: Item) = viewedArticlesDao.insert(item)
//
//    override suspend fun deleteItem(item: Item) = viewedArticlesDao.delete(item)
//
//    override suspend fun updateItem(item: Item) = viewedArticlesDao.update(item)
    override suspend fun insertViewedArticle(
    article: ViewedArticles
    ) = localDao.insertViewedArticle(article)

    override fun getAllViewedArticles(): Flow<List<ViewedArticles>> =
        localDao.getAllViewedArticles()

    override suspend fun insertDeletedScan(
        scan: DeletedScans
    ) = localDao.insertDeletedScan(scan)

    override fun getAllDeletedScans(): Flow<List<DeletedScans>> =
        localDao.getAllDeletedScans()
}