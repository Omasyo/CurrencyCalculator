package com.omasyo.currencycalculator.ui.convert

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ConvertCurrencyRoute(
    onHistoryTap: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ConvertCurrencyViewModel = hiltViewModel()
) {
    ConvertCurrencyScreen(
        onHistoryTap = onHistoryTap,
        onFromAmountChange = viewModel::changeFromAmount,
        onToAmountChange = viewModel::changeToAmount,
        onFromCurrencyChange = viewModel::changeFromCurrency,
        onToCurrencyChange = viewModel::changeToCurrency,
        currencyUiState = viewModel.currencyUiState.collectAsStateWithLifecycle().value,
        onConvertTap = viewModel::convert,
        onSwapCurrencies = viewModel::swapCurrencies,
        dayRange = viewModel.dayRange.collectAsStateWithLifecycle().value,
        onDayRangeChange = viewModel::changeDayRange,
        currencyOptions = viewModel.currencyOptions.collectAsStateWithLifecycle().value,
        chartData = viewModel.chartData.collectAsStateWithLifecycle().value,
        modifier = modifier,
    )
}