package com.thenewsapp.data

import com.thenewsapp.data.net.ApiKeyInterceptor
import com.thenewsapp.data.repository.NewsRepository
import com.thenewsapp.data.repository.QueryRepository
import com.thenewsapp.domain.GetNewsUseCase
import com.thenewsapp.domain.SaveQueryUseCase
import com.thenewsapp.presentation.NewsApplication
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DependencyProvider {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(NewsService.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(httpClient)
            .build()
    }

    private val gsonConverterFactory by lazy { GsonConverterFactory.create() }

    private val apiKeyInterceptor by lazy { ApiKeyInterceptor() }

    private val httpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    fun provideGetNewsUseCase() =
        GetNewsUseCase(
            provideNewsRepository(
                provideService(NewsService::class.java)
            )
        )

    fun provideSaveQueryUseCase(application: NewsApplication) =
        SaveQueryUseCase(
            provideQueryRepository(application)
        )

    private fun <T> provideService(service: Class<T>): T {
        return retrofit.create(service)
    }

    private fun provideNewsRepository(newsService: NewsService) = NewsRepository(newsService)

    private fun provideQueryRepository(application: NewsApplication) =
        QueryRepository(application.database.searchTermDao())


}