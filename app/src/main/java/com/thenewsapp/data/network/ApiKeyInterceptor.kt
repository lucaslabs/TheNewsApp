package com.thenewsapp.data.network

import com.thenewsapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor to add the API key to the original request as a query parameter.
 */
class ApiKeyInterceptor : Interceptor {

    companion object {
        private const val API_KEY = "apiKey"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        // Url customization
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter(API_KEY, BuildConfig.NEWS_API_KEY)
            .build()

        // Request customization
        val request = originalRequest.newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}