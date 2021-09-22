package com.thenewsapp.presentation.shownews

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.thenewsapp.data.model.News
import com.thenewsapp.data.net.model.Resource
import com.thenewsapp.data.net.model.Result
import com.thenewsapp.data.repository.NewsRepository

class SharedNewsViewModel(
    private val savedStateHandle: SavedStateHandle?,
    private val newsRepository: NewsRepository,
) : ViewModel() {

    companion object {
        private const val QUERY_KEY = "query"
    }

    private val _news = MutableLiveData<Resource<ArrayList<News>>>()
    val news: LiveData<Resource<ArrayList<News>>> = _news

    private val _selectedNews = MutableLiveData<News>()

    fun searchNews(query: String) = liveData {
        emit(Result.Loading())

        saveQuery(query)

        runCatching {
            newsRepository.searchNews(query)
        }.onSuccess {
            emit(Result.Success(it.news))
        }.onFailure {
            emit(Result.Error(it))
        }
    }

    fun getNews(): ArrayList<News>? = _news.value?.data

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
    ) : AbstractSavedStateViewModelFactory(owner, defaultState) {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle,
        ): T {
            return SharedNewsViewModel(handle, newsRepository) as T
        }
    }
}