package com.thenewsapp.presentation

import android.app.Application
import com.thenewsapp.data.db.SearchTermRoomDatabase
import com.thenewsapp.data.repository.SearchTermRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NewsApplication : Application() {

    // No need to cancel this scope as it'll be torn down with the process
    private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { SearchTermRoomDatabase.getDatabase(this, applicationScope) }
    val searchTermRepository by lazy { SearchTermRepository(database.searchTermDao()) }
}