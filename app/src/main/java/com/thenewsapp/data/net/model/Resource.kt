package com.thenewsapp.data.net.model

/**
 * Encapsulates both the data and its state.
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T> : Resource<T>()
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String?) : Resource<T>(message = message)
}