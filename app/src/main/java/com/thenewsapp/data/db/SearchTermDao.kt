package com.thenewsapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchTermDao {

    @Query("SELECT * FROM search_table ORDER BY `query` ASC")
    fun getAllSearchTerms(): Flow<List<Search>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(searchTerm: Search)

    @Query("DELETE FROM search_table")
    suspend fun deleteAll()
}