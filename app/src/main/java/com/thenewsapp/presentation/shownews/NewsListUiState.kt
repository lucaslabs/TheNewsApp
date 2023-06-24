package com.thenewsapp.presentation.shownews

import com.thenewsapp.data.model.News

// TODO Move to Composable screen
sealed class NewsListUiState {
    object Idle : NewsListUiState()
    object Loading : NewsListUiState()
    data class Success(val news: List<News>) : NewsListUiState()
    data class Error(val throwable: Throwable) : NewsListUiState()

}
