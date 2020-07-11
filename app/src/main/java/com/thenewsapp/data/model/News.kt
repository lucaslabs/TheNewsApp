package com.thenewsapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Source(val name: String) : Parcelable

@Parcelize
data class News(
    val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: Date,
    val content: String
) : Parcelable