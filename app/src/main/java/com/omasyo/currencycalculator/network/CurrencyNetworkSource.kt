package com.omasyo.currencycalculator.network

import com.omasyo.currencycalculator.network.model.LatestRatesResponse
import com.omasyo.currencycalculator.common.Response
import com.omasyo.currencycalculator.network.model.TimeSeriesResponse

interface CurrencyNetworkSource {
    suspend fun getLatest(base: String) : Response<LatestRatesResponse>

    suspend fun getRateForLast30(from: String, to: String) : Response<TimeSeriesResponse>

    suspend fun getRateForLast90(from: String, to: String) : Response<TimeSeriesResponse>
}