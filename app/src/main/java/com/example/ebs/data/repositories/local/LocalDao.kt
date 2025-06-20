package com.example.ebs.data.repositories.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ebs.data.structure.local.localItems.DeletedScans
import com.example.ebs.data.structure.local.localItems.ViewedArticles
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalDao {
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insert(item: Item)
//
//    @Update
//    suspend fun update(item: Item)
//
//    @Delete
//    suspend fun delete(item: Item)
//
//    @Query("SELECT * from items WHERE id = :id")
//    fun getItem(id: Int): Flow<Item>
//
//    @Query("SELECT * from items ORDER BY name ASC")
//    fun getAllItems(): Flow<List<Item>>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertViewedArticle(article: ViewedArticles)

    @Query("SELECT * FROM viewed_articles")
    fun getAllViewedArticles(): Flow<List<ViewedArticles>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDeletedScan(scan: DeletedScans)

    @Query("SELECT * FROM deleted_scans")
    fun getAllDeletedScans(): Flow<List<DeletedScans>>
}