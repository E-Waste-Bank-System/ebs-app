package com.example.ebs.data.repositories.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ebs.data.structure.local.localItems.DeletedScans
import com.example.ebs.data.structure.local.localItems.ViewedArticles

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [ViewedArticles::class, DeletedScans::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun itemDao(): LocalDao

    companion object {
        @Volatile
        private var Instance: LocalDatabase? = null

        fun getDatabase(context: Context): LocalDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, LocalDatabase::class.java, "local_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}