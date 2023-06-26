package com.thenewsapp.data.network.model

/**
 * Network model of a news.
 */
data class NewsResponse(
    val author: String?,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String?,
    val content: String
)