package com.thenewsapp.domain.mapper

import com.thenewsapp.data.database.model.NewsEntity
import com.thenewsapp.data.network.model.NewsResponse
import com.thenewsapp.domain.model.News

/**
 * Extension functions to convert between the different models.
 *
 * NewsEntity: database model.
 * NewsResponse: network model.
 * News: domain model.
 */


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
fun NewsEntity.toDomain() = News(
    author = author,
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
    content = content
)