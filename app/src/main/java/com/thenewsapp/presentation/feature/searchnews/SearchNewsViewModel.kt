package com.thenewsapp.presentation.feature.searchnews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thenewsapp.domain.SearchNewsUseCase
import com.thenewsapp.domain.model.News
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel that handles user interaction and push data to UI using Flow.
 */
@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val searchNewsUseCase: SearchNewsUseCase
) : ViewModel() {

    var state = MutableStateFlow<SearchNewsUiState>(SearchNewsUiState.Idle)
        private set

    var selectedNews = MutableStateFlow<News?>(null)
        private set

    fun searchNews(query: String) {
        viewModelScope.launch {
            if (query.isNotEmpty()) {
                state.value = SearchNewsUiState.Loading

                searchNewsUseCase(query)
                    .catch {
                        state.value = SearchNewsUiState.Error(it)
                    }
                    .collect {
                        state.value = SearchNewsUiState.Success(it)
                    }
            }
        }
    }
}