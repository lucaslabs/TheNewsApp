package com.thenewsapp.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity model of a news.
 */
@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long? = null,
    val query : String? = null,
    val author: String?,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String?,
    val content: String
)