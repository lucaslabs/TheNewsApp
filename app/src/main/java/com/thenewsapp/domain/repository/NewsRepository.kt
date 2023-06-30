package com.thenewsapp.domain.repository

import com.thenewsapp.data.network.model.NewsListResponse
import com.thenewsapp.domain.model.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun searchNews(query: String): Flow<NewsListResponse>

    fun searchNewsOfflineFirst(query: String): Flow<List<News>>

    suspend fun refreshNews(query: String)
}