package com.syriusdevelopment.pokedex.data

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    companion object {
        fun <T> success(data: T) = Success(data)
        fun <T> loading() = Loading<T>()
        fun <T> error(message: String) = Error<T>(message)
    }
}