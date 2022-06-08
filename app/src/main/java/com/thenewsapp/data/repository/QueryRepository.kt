package com.thenewsapp.data.repository

import androidx.annotation.WorkerThread
import com.thenewsapp.data.db.Query
import com.thenewsapp.data.db.QueryDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QueryRepository @Inject constructor(private val queryDao: QueryDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allQueryTerms: Flow<List<Query>>? = queryDao.getAllSearchTerms()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(query: Query) {
        queryDao.insert(query)
    }
}