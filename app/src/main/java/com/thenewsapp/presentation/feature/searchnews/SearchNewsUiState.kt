package com.thenewsapp.presentation.feature.searchnews

import com.thenewsapp.domain.model.News

/**
 * Represents the possible states of search news screen.
 */
sealed class SearchNewsUiState {
    object Idle : SearchNewsUiState()
    object Loading : SearchNewsUiState()
    data class Success(val news: List<News>) : SearchNewsUiState()
    data class Error(val throwable: Throwable) : SearchNewsUiState()

}
