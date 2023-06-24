package com.thenewsapp.data.network.model

import com.google.gson.annotations.SerializedName

data class NewsListResponse(@SerializedName("articles") val news: List<NewsResponse>)