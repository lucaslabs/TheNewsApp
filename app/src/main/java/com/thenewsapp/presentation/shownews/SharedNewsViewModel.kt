package com.thenewsapp.presentation.shownews

import androidx.lifecycle.*
import com.thenewsapp.data.db.Query
import com.thenewsapp.data.model.News
import com.thenewsapp.data.model.Result
import com.thenewsapp.domain.GetNewsUseCase
import com.thenewsapp.domain.SaveQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedNewsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle?,
    private val getNewsUseCase: GetNewsUseCase,
    private val saveQueryUseCase: SaveQueryUseCase,
) : ViewModel() {

    companion object {
        private const val QUERY_KEY = "query"
    }

    private val _selectedNews = MutableLiveData<News>()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(query: Query) = viewModelScope.launch {
        saveQueryUseCase(query)
    }

    fun searchNews(query: String) = liveData {
        if (query.isNotEmpty()) {
            emit(Result.Loading)

            saveQuery(query)

            kotlin.runCatching {
               getNewsUseCase(query)
            }.onSuccess {
                emit(Result.Success(it.news))
            }.onFailure {
                emit(Result.Error(it))
            }
        }
    }

    fun setSelectedNews(news: News) {
        _selectedNews.value = news
    }

    fun getSelectedNews() = _selectedNews.value

    fun getQuery() = savedStateHandle?.get<String>(QUERY_KEY)

    private fun saveQuery(query: String) = savedStateHandle?.set(QUERY_KEY, query)
}