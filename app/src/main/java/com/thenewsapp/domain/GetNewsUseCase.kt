package com.thenewsapp.domain

import com.thenewsapp.data.model.NewsResponse
import com.thenewsapp.data.repository.NewsRepository

class GetNewsUseCase(private val newsRepository: NewsRepository) {

    suspend operator fun invoke(query: String): NewsResponse {
        return newsRepository.searchNews(query)
    }
}