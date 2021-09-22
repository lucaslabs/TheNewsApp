package com.thenewsapp.data.net.model

sealed class Result<out T : Any> {

    class Loading<out T : Any> : Result<T>()
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Throwable? = null) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Errors[server throwable=$exception]"
            is Loading -> "Loading [...]"
        }
    }
}
