package com.thenewsapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QueryDao {

    @Query("SELECT * FROM query_table ORDER BY `query` ASC")
    fun getAllSearchTerms(): Flow<List<com.thenewsapp.data.db.Query>>?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(queryTerm: com.thenewsapp.data.db.Query)

    @Query("DELETE FROM query_table")
    suspend fun deleteAll()
}