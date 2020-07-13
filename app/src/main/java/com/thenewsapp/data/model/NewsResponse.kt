package com.thenewsapp.data.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(@SerializedName("articles") val news: ArrayList<News>)