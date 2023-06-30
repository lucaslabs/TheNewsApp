package com.thenewsapp.domain

import com.thenewsapp.domain.repository.NewsRepository
import com.thenewsapp.domain.model.News
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case to search for news by a given query.
 */
class SearchNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {

    operator fun invoke(query: String): Flow<List<News>> =
        newsRepository.searchNewsOfflineFirst(query)
}
