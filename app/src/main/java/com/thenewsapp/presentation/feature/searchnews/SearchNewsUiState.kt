package com.thenewsapp.presentation.feature.searchnews

import com.thenewsapp.data.model.News

// TODO Move to Composable screen
sealed class SearchNewsUiState {
    object Idle : SearchNewsUiState()
    object Loading : SearchNewsUiState()
    data class Success(val news: List<News>) : SearchNewsUiState()
    data class Error(val throwable: Throwable) : SearchNewsUiState()

}
