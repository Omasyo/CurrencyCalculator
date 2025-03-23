package com.omasyo.currencycalculator.common

sealed interface Response<out T> {
    data class Error(val message: String) : Response<Nothing>

    data class Success<T>(val content: T) : Response<T>
}

fun <T, U> Response<T>.map(exec: (T) -> U): Response<U> = when (this) {
    is Response.Error -> this
    is Response.Success -> Response.Success(exec(content))
}
