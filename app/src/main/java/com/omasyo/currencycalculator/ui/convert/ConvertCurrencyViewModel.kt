package com.omasyo.currencycalculator.ui.convert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omasyo.currencycalculator.common.Response
import com.omasyo.currencycalculator.data.ConversionRepository
import com.omasyo.currencycalculator.model.ChartPoint
import com.omasyo.currencycalculator.model.UiState
import com.omasyo.currencycalculator.ui.components.DayRange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConvertCurrencyViewModel @Inject constructor(
    private val repository: ConversionRepository,
) : ViewModel() {

    private val _currencyUiState =
        MutableStateFlow(CurrencyUiState(Currency(isEnabled = false), Currency(isEnabled = true)))
    val currencyUiState = _currencyUiState.asStateFlow()

    val currencyOptions: StateFlow<List<String>> = repository.getCurrencies("")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val conversionRate: StateFlow<Double> = _currencyUiState.map { uiState ->
        val symbol = if (uiState.from.isEnabled) uiState.from.currency else uiState.to.currency
        val rate = repository.getConversionRate(symbol).first()
        rate
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 1.0)


    private val _dayRange = MutableStateFlow(DayRange.Thirty)
    val dayRange = _dayRange.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val chartData: StateFlow<UiState<List<ChartPoint>>> = dayRange.transformLatest { range ->
        emit(UiState.Loading)

        val call = when (range) {
            DayRange.Thirty -> repository::getRateForLast30
            DayRange.Ninety -> repository::getRateForLast90
        }
        val data = when (val response = call("", "")) {
            is Response.Error -> UiState.Error(response.message)
            is Response.Success -> UiState.Success(response.content)
        }
        emit(data)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), UiState.Loading)

    fun changeFromAmount(newAmount: String) {
        val isBase = !currencyUiState.value.from.isEnabled
        _currencyUiState.value = CurrencyUiState(
            from = currencyUiState.value.from.copy(amount = newAmount),
            to = currencyUiState.value.to.copy(amount = newAmount.applyConversion(if (!isBase) conversionRate.value else 1 / conversionRate.value)),
        )
    }

    fun changeToAmount(newAmount: String) {
        val isBase = !currencyUiState.value.to.isEnabled
        _currencyUiState.value = CurrencyUiState(
            from = currencyUiState.value.from.copy(amount = newAmount.applyConversion(if (!isBase) conversionRate.value else 1 / conversionRate.value)),
            to = currencyUiState.value.to.copy(amount = newAmount),
        )
    }

    fun changeFromCurrency(currency: String) {
        val isBase = !currencyUiState.value.from.isEnabled
        val amount = currencyUiState.value.to.amount

        viewModelScope.launch(Dispatchers.IO) {
            val rate = repository.getConversionRate(currency) .first()
            _currencyUiState.value = currencyUiState.value.copy(
                from = currencyUiState.value.from.copy(
                    currency = currency,
                    amount = amount.applyConversion(if (!isBase) rate else 1 / rate)
                ),
            )
        }
    }

    fun changeToCurrency(currency: String) {
        val isBase = !currencyUiState.value.to.isEnabled
        val amount = currencyUiState.value.from.amount

        viewModelScope.launch(Dispatchers.IO) {
            val rate = repository.getConversionRate(currency).first()
            _currencyUiState.value = currencyUiState.value.copy(
                to = currencyUiState.value.to.copy(
                    currency = currency,
                    amount = amount.applyConversion(if (!isBase) rate else 1 / rate)
                ),
            )
        }
    }

    fun convert() {}

    fun swapCurrencies() {
        _currencyUiState.value =
            CurrencyUiState(from = currencyUiState.value.to, to = currencyUiState.value.from)
    }

    fun changeDayRange(dayRange: DayRange) {
        _dayRange.value = dayRange
    }

    init {
        viewModelScope.launch {
            launch {
                repository.getBaseCurrency().collectLatest { base ->
                    _currencyUiState.value = _currencyUiState.value.copy(
                        from = _currencyUiState.value.from.copy(currency = base ?: ""),
                        to = _currencyUiState.value.to.copy(currency = base ?: ""),
                    )
                }
            }
            launch {
                conversionRate.collect()
            }
        }
    }
}

fun String.applyConversion(rate: Double) = ((toDoubleOrNull() ?: 0.0) * rate).toString()