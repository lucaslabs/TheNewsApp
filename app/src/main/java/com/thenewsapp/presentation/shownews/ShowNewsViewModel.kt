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

class ShowNewsViewModel(private val newsService: NewsService) : ViewModel(),
    Callback<NewsResponse> {

    private var news = MutableLiveData<Resource<List<News>>>()

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

    class Factory(private val newsService: NewsService) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            ShowNewsViewModel(newsService) as T
    }
}