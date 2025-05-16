package com.example.ebs.data.repositories.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ebs.data.structure.local.localItems.Item

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class ItemsDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var Instance: ItemsDatabase? = null

        fun getDatabase(context: Context): ItemsDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ItemsDatabase::class.java, "item_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}