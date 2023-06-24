package com.thenewsapp.data.mapper

import com.thenewsapp.data.database.model.NewsEntity
import com.thenewsapp.data.model.News
import com.thenewsapp.data.network.model.NewsResponse

/**
 * Converts the network model to the local model for persisting by the local data source.
 */
fun NewsResponse.asEntity() = NewsEntity(
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    content = content
)

/**
 * Converts the local model to the external model for use by layers external to the data layer.
 */
fun NewsEntity.asExternalModel() = News(
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    content = content
)