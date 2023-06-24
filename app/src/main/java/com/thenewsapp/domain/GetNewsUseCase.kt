package com.thenewsapp.domain

import com.thenewsapp.data.model.News
import com.thenewsapp.data.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// TODO Add a refresh use case?
class GetNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {

    operator fun invoke(query: String): Flow<List<News>> =
        newsRepository.searchNewsOfflineFirst(query)
}
