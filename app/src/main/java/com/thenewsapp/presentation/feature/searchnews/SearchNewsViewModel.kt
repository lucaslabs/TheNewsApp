package com.thenewsapp.presentation.feature.searchnews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thenewsapp.data.model.News
import com.thenewsapp.domain.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {

    var state = MutableStateFlow<SearchNewsUiState>(SearchNewsUiState.Idle)
        private set

    var selectedNews = MutableStateFlow<News?>(null)
        private set

    fun searchNews(query: String) {
        viewModelScope.launch {
            if (query.isNotEmpty()) {
                state.value = SearchNewsUiState.Loading

                getNewsUseCase(query)
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