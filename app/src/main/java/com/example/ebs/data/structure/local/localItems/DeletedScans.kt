package com.example.ebs.data.structure.local.localItems

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity data class represents a single row in the database.
 */
@Entity(tableName = "deleted_scans")
data class DeletedScans(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val deletedScans: String
)

