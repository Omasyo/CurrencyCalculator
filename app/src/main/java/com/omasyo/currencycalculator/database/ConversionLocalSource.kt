package com.omasyo.currencycalculator.database

import com.omasyo.currencycalculator.database.conversionhistory.ConversionHistory
import com.omasyo.currencycalculator.database.conversionrate.ConversionRate
import kotlinx.coroutines.flow.Flow

interface ConversionLocalSource {
    fun getCurrencies(): Flow<List<String>>

    fun getBaseCurrency(): Flow<String?>

    fun getConversionRate(currency: String) : Flow<Double>

    fun updateConversionRates(rates: List<ConversionRate>)

    fun clearConversionRates()

    suspend fun getHistory(): Flow<List<ConversionHistory>>

    fun insertConversion(data: ConversionHistory)
}
