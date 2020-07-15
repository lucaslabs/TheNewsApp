package com.thenewsapp.data.model

import java.util.*

data class Source(val name: String)

data class News(
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: Date,
    val content: String
)