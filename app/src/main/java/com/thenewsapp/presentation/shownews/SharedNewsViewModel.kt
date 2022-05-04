package com.thenewsapp.presentation.shownews

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.thenewsapp.data.db.Query
import com.thenewsapp.data.model.News
import com.thenewsapp.data.model.Result
import com.thenewsapp.domain.GetNewsUseCase
import com.thenewsapp.domain.SaveQueryUseCase
import kotlinx.coroutines.launch

class SharedNewsViewModel(
    private val savedStateHandle: SavedStateHandle?,
    private val getNewsUseCase: GetNewsUseCase,
    private val saveQueryUseCase: SaveQueryUseCase
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

    class Factory(
        owner: SavedStateRegistryOwner,
        defaultState: Bundle?,
        private val getNewsUseCase: GetNewsUseCase,
        private val saveQueryUseCase: SaveQueryUseCase
    ) : AbstractSavedStateViewModelFactory(owner, defaultState) {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle,
        ): T {
            if (modelClass.isAssignableFrom(SharedNewsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SharedNewsViewModel(handle, getNewsUseCase, saveQueryUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}