package com.thenewsapp.data.model

data class News(
    val author: String?,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String?,
    val content: String
)