package com.thenewsapp.presentation.shownews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thenewsapp.data.NewsService
import com.thenewsapp.data.model.News
import com.thenewsapp.data.model.NewsResponse
import com.thenewsapp.data.net.model.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SharedNewsViewModel(private val newsService: NewsService) : ViewModel(),
    Callback<NewsResponse> {

    private var _news = MutableLiveData<Resource<ArrayList<News>>>()
    val news: LiveData<Resource<ArrayList<News>>> = _news

    private var _selectedNews = MutableLiveData<News>()
    val selectedNews: LiveData<News> = _selectedNews

    fun searchNews(query: String) {
        _news.value = Resource.Loading()
        newsService.searchNews(query).enqueue(this)
    }

    fun getNews(): ArrayList<News>? {
        return _news.value?.data
    }

    override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
        if (response.isSuccessful) {
            response.body()?.let {
                _news.value = Resource.Success(it.news)
            }
        }
    }

    override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
        _news.value = Resource.Error(t)
    }

    fun setSelectedNews(news: News) {
        _selectedNews.value = news
    }

    class Factory(private val newsService: NewsService) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            SharedNewsViewModel(newsService) as T
    }
}