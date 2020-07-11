package com.thenewsapp.presentation.shownews

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.thenewsapp.data.NewsService
import com.thenewsapp.data.model.News
import com.thenewsapp.data.model.NewsResponse
import com.thenewsapp.data.net.model.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowNewsViewModel(
    private val newsService: NewsService,
    private val savedStateHandle: SavedStateHandle
) :
    ViewModel(), Callback<NewsResponse> {

    companion object {
        private const val NEWS_KEY = "news"
    }

    private var news = MutableLiveData<Resource<List<News>>>()

    fun getNews(query: String): LiveData<Resource<List<News>>> {
        // TODO news = savedStateHandle.getLiveData(NEWS_KEY)
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
                // TODO savedStateHandle[NEWS_KEY] = news

                news.value = Resource.Success(it.news)
            }
        }
    }

    class Factory(
        owner: SavedStateRegistryOwner,
        defaultArgs: Bundle? = null,
        private val newsService: NewsService
    ) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            return ShowNewsViewModel(
                newsService,
                handle
            ) as T
        }
    }
}