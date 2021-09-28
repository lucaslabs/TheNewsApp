package com.thenewsapp.presentation.shownews

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.thenewsapp.data.db.Search
import com.thenewsapp.data.model.News
import com.thenewsapp.data.model.Result
import com.thenewsapp.data.repository.NewsRepository
import com.thenewsapp.data.repository.SearchTermRepository
import kotlinx.coroutines.launch

class SharedNewsViewModel(
    private val savedStateHandle: SavedStateHandle?,
    private val newsRepository: NewsRepository,
    private val searchTermRepository: SearchTermRepository,
) : ViewModel() {

    companion object {
        private const val QUERY_KEY = "query"
    }

    private val _selectedNews = MutableLiveData<News>()

    val allSearchTerms: LiveData<List<Search>> =
        searchTermRepository.allSearchTerms.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(searchTerm: Search) = viewModelScope.launch {
        searchTermRepository.insert(searchTerm)
    }

    fun searchNews(query: String) = liveData {
        if (query.isNotEmpty()) {
            emit(Result.Loading)

            saveQuery(query)

            runCatching {
                newsRepository.searchNews(query)
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
        private val newsRepository: NewsRepository,
        private val searchTermRepository: SearchTermRepository,
    ) : AbstractSavedStateViewModelFactory(owner, defaultState) {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle,
        ): T {
            if (modelClass.isAssignableFrom(SharedNewsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SharedNewsViewModel(handle, newsRepository, searchTermRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}