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

    private var news = MutableLiveData<Resource<ArrayList<News>>>()

    private var selectedNews = MutableLiveData<News>()

    fun searchNews(query: String): LiveData<Resource<ArrayList<News>>> {
        news.value = Resource.Loading()
        newsService.getNews(query).enqueue(this)
        return news
    }

    fun getNews(): ArrayList<News>? {
        return news.value?.data
    }

    override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
        if (response.isSuccessful) {
            response.body()?.let {
                news.value = Resource.Success(it.news)
            }
        }
    }

    override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
        news.value = Resource.Error(t)
    }

    fun setSelectedNews(news: News) {
        selectedNews.value = news
    }

    fun getSelectedNews(): LiveData<News> = selectedNews

    class Factory(private val newsService: NewsService) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            SharedNewsViewModel(newsService) as T
    }
}