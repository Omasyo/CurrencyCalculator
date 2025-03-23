package com.omasyo.currencycalculator.ui.convert

data class CurrencyUiState(
    val from: Currency,
    val to: Currency,
)

data class Currency(
    val amount: String = "",
    val currency: String = "",
    val isEnabled: Boolean = true,
)