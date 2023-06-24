package com.thenewsapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thenewsapp.data.database.model.NewsEntity

@Database(entities = [NewsEntity::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase(){

    companion object {
        const val DATABASE_NAME = "news_database"
    }

    abstract fun newsDao() : NewsDao
}