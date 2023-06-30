package com.thenewsapp.data.repository

import com.thenewsapp.data.database.NewsDao
import com.thenewsapp.data.network.NewsService
import com.thenewsapp.data.network.model.NewsListResponse
import com.thenewsapp.domain.mapper.asEntity
import com.thenewsapp.domain.mapper.toDomain
import com.thenewsapp.domain.model.News
import com.thenewsapp.domain.repository.NewsRepository
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
class NewsRepositoryImpl @Inject constructor(
    private val newsService: NewsService,
    private val newsDao: NewsDao
): NewsRepository {

    override fun searchNews(query: String): Flow<NewsListResponse> = flow {
        emit(newsService.searchNews(query))
    }.flowOn(Dispatchers.IO)

    /**
     * Searches for news by a given query with offline first approach.
     *
     * If there is any result, return it to the consumer.
     * If the result is empty, call the service API.
     */
    override fun searchNewsOfflineFirst(query: String): Flow<List<News>> {
        return newsDao.getNewsByQuery(query)
            .map { newsList ->
                newsList.map { it.toDomain() }
            }
            .onEach { news ->
                if (news.isEmpty()) {
                    refreshNews(query)
                }
            }.flowOn(Dispatchers.IO)
    }

    override suspend fun refreshNews(query: String) {
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