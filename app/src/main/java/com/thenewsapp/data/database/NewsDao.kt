package com.thenewsapp.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.thenewsapp.data.database.model.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    // TODO Filter by latest timestamp
    @Query("SELECT * FROM news WHERE `query` LIKE :query")
    fun getLatestNews(query: String) : Flow<List<NewsEntity>>

    @Upsert
    suspend fun saveNews(news: List<NewsEntity>)
}