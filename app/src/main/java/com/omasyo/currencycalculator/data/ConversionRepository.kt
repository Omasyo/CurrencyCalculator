package com.omasyo.currencycalculator.data

import com.omasyo.currencycalculator.database.conversionhistory.ConversionHistory
import com.omasyo.currencycalculator.common.Response
import com.omasyo.currencycalculator.model.ChartPoint
import com.omasyo.currencycalculator.network.model.TimeSeriesResponse
import kotlinx.coroutines.flow.Flow

interface ConversionRepository {
    fun getCurrencies(base: String): Flow<List<String>>

    fun getBaseCurrency(): Flow<String?>

    fun getConversionRate(currency: String): Flow<Double>

    suspend fun getHistory(): Flow<List<ConversionHistory>>

    suspend fun createConversionHistory(data: ConversionHistory)

    suspend fun getRateForLast30(from: String, to: String): Response<List<ChartPoint>>

    suspend fun getRateForLast90(from: String, to: String): Response<List<ChartPoint>>
}