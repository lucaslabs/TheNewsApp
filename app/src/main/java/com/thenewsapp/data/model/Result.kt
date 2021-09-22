package com.thenewsapp.data.model

sealed class Result<out R> {

    object Loading : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Errors[server throwable=$exception]"
            is Loading -> "Loading [...]"
        }
    }
}