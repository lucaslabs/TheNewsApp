package com.thenewsapp.data.net.model

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T> : Resource<T>()
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(val throwable: Throwable) : Resource<T>(message = throwable.message)
}