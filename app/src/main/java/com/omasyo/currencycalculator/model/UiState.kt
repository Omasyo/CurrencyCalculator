package com.omasyo.currencycalculator.model

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>

    data class Error(val message: String) : UiState<Nothing>

    data class Success<T>(val content: T) : UiState<T>
}