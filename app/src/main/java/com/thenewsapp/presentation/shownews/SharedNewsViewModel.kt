package com.thenewsapp.presentation.shownews

import androidx.lifecycle.MutableLiveData
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
class SharedNewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {

    var newsState = MutableStateFlow<NewsListUiState>(NewsListUiState.Idle)
        private set

    private val _selectedNews = MutableLiveData<News>()

    fun searchNews(query: String) {
        viewModelScope.launch {
            if (query.isNotEmpty()) {
                newsState.value = NewsListUiState.Loading
                getNewsUseCase(query)
                    .catch {
                        newsState.value = NewsListUiState.Error(it)
                    }
                    .collect {
                        newsState.value = NewsListUiState.Success(it)
                    }
            }
        }
    }

    fun setSelectedNews(news: News) {
        _selectedNews.value = news
    }

    fun getSelectedNews() = _selectedNews.value
}