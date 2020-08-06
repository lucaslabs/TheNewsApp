package com.thenewsapp.data

import com.thenewsapp.data.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    companion object {
        const val BASE_URL = "https://newsapi.org"
    }

    @GET("/v2/everything")
    suspend fun searchNews(@Query("q") query: String): Response<NewsResponse>
}