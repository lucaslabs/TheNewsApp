package com.thenewsapp.data.repository

import androidx.annotation.WorkerThread
import com.thenewsapp.data.db.Search
import com.thenewsapp.data.db.SearchTermDao
import kotlinx.coroutines.flow.Flow

class SearchTermRepository(private val searchTermDao: SearchTermDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allSearchTerms: Flow<List<Search>>? = searchTermDao.getAllSearchTerms()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(searchTerm: Search) {
        searchTermDao.insert(searchTerm)
    }
}