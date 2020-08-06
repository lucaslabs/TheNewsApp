package com.thenewsapp.presentation.shownews

import androidx.lifecycle.*
import com.thenewsapp.data.NewsService
import com.thenewsapp.data.model.News
import com.thenewsapp.data.model.NewsResponse
import com.thenewsapp.data.net.model.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class SharedNewsViewModel(private val newsService: NewsService) : ViewModel() {

    private var _news = MutableLiveData<Resource<ArrayList<News>>>()
    val news: LiveData<Resource<ArrayList<News>>> = _news

    private var _selectedNews = MutableLiveData<News>()
    val selectedNews: LiveData<News> = _selectedNews

    fun searchNews(query: String) {
        viewModelScope.launch {
            _news.value = Resource.Loading()
            val response = newsService.searchNews(query)
            if (response.isSuccessful) {
                response.body()?.let {
                    _news.value = Resource.Success(it.news)
                } ?: run {
                    error(response)
                }
            } else {
                error(response)
            }
        }
    }

    fun getNews(): ArrayList<News>? {
        return _news.value?.data
    }

    fun setSelectedNews(news: News) {
        _selectedNews.value = news
    }

    private fun error(response: Response<NewsResponse>) {
        _news.value = Resource.Error(response.message())
    }

    class Factory(private val newsService: NewsService) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            SharedNewsViewModel(newsService) as T
    }
}