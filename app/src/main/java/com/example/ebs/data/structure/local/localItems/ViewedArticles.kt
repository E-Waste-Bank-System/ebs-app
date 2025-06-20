package com.example.ebs.data.structure.local.localItems

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "viewed_articles")
data class ViewedArticles(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val viewedArticles: String
)