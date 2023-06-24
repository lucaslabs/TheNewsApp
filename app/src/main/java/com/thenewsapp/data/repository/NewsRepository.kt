package com.thenewsapp.data.repository

import com.thenewsapp.data.NewsService
import com.thenewsapp.data.database.NewsDao
import com.thenewsapp.data.mapper.asEntity
import com.thenewsapp.data.mapper.asExternalModel
import com.thenewsapp.data.model.News
import com.thenewsapp.data.network.model.NewsListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Offline-first repository, this means, the data lookup will be to the database first.
 * The local database is the single source of truth.
 */
class NewsRepository @Inject constructor(
    private val newsService: NewsService,
    private val newsDao: NewsDao
) {

    fun searchNews(query: String): Flow<NewsListResponse> = flow {
        emit(newsService.searchNews(query))
    }.flowOn(Dispatchers.IO)

    fun searchNewsOfflineFirst(query: String): Flow<List<News>> {
        return newsDao.getLatestNews(query)
            .map { newsList ->
                newsList.map { it.asExternalModel() }
            }
            .onEach { news ->
                if (news.isEmpty()) {
                    refreshNews(query)
                }
            }
    }

    suspend fun refreshNews(query: String) {
        val timestamp = System.currentTimeMillis()
        newsService.searchNews(query).news
            .map {
                it.asEntity().copy(
                    query = query,
                    timestamp = timestamp
                )
            }
            .also {
                newsDao.saveNews(it)
            }
    }
}