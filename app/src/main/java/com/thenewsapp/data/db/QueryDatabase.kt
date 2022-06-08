package com.thenewsapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Query::class], version = 1, exportSchema = false)
abstract class QueryDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "query_database"
    }

    abstract fun queryDao(): QueryDao
}