package com.thenewsapp.data

import com.thenewsapp.data.network.model.NewsListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    companion object {
        const val BASE_URL = "https://newsapi.org"
    }

    @GET("/v2/everything")
    suspend fun searchNews(@Query("q") query: String): NewsListResponse
}