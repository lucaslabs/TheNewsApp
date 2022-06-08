package com.thenewsapp.data.repository

import com.thenewsapp.data.NewsService
import com.thenewsapp.data.model.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsService: NewsService) {

    suspend fun searchNews(query: String): NewsResponse = withContext(Dispatchers.IO) {
        newsService.searchNews(query)
    }
}