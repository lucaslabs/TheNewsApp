package com.thenewsapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Query::class], version = 1, exportSchema = false)
public abstract class SearchTermRoomDatabase : RoomDatabase() {

    abstract fun searchTermDao(): QueryDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: SearchTermRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): SearchTermRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SearchTermRoomDatabase::class.java,
                    "search_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}