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

    private var news = MutableLiveData<Resource<List<News>>>()

    private var selectedNews = MutableLiveData<News>()

    fun getNews(query: String): LiveData<Resource<List<News>>> {
        news.value = Resource.Loading()
        newsService.getNews(query).enqueue(this)
        return news
    }

    override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
        news.value = Resource.Error(t)
    }

    override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
        if (response.isSuccessful) {
            response.body()?.let {
                news.value = Resource.Success(it.news)
            }
        }
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