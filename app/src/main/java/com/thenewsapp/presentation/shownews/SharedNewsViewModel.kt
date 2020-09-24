package com.thenewsapp.presentation.shownews

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.thenewsapp.data.NewsService
import com.thenewsapp.data.model.News
import com.thenewsapp.data.net.model.Resource
import kotlinx.coroutines.launch

class SharedNewsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val newsService: NewsService
) : ViewModel() {

    companion object {
        private const val QUERY_KEY = "query"
    }

    private val _news = MutableLiveData<Resource<ArrayList<News>>>()
    val news: LiveData<Resource<ArrayList<News>>> = _news

    private val _selectedNews = MutableLiveData<News>()

    fun searchNews(query: String) {

        saveQuery(query)

        viewModelScope.launch {
            try {
                _news.value = Resource.Loading()
                val response = newsService.searchNews(query)
                if (response.isSuccessful && response.body() != null) {
                    _news.value = Resource.Success(response.body()!!.news)
                } else {
                    _news.value = Resource.Error(response.message())
                }
            } catch (e: Exception) {
                _news.value = Resource.Error(e.message)
            }
        }
    }

    fun getNews(): ArrayList<News>? = _news.value?.data

    fun setSelectedNews(news: News) {
        _selectedNews.value = news
    }

    fun getSelectedNews() = _selectedNews.value

    fun getQuery() = savedStateHandle.get<String>(QUERY_KEY)

    private fun saveQuery(query: String) = savedStateHandle.set(QUERY_KEY, query)

    class Factory(
        owner: SavedStateRegistryOwner,
        defaultState: Bundle?,
        private val newsService: NewsService
    ) : AbstractSavedStateViewModelFactory(owner, defaultState) {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return SharedNewsViewModel(handle, newsService) as T
        }
    }
}