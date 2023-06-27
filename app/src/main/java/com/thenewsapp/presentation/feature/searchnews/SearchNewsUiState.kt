package com.thenewsapp.presentation.feature.searchnews

import com.thenewsapp.domain.model.News

/**
 * Represents the possible states of search news screen.
 */
data class SearchNewsUiState(
    var isIdle : Boolean = false,
    var isLoading: Boolean = false,
    var news: List<News> = emptyList(),
    var error: String? = null
)