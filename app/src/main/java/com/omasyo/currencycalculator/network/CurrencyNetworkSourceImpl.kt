package com.omasyo.currencycalculator.network

import android.util.Log
import com.omasyo.currencycalculator.common.Response
import com.omasyo.currencycalculator.network.dummy.NinetyDaysResponse
import com.omasyo.currencycalculator.network.dummy.ThirtyDaysResponse
import com.omasyo.currencycalculator.network.model.LatestRatesResponse
import com.omasyo.currencycalculator.network.model.TimeSeriesResponse
import kotlinx.serialization.json.Json
import javax.inject.Inject

internal class CurrencyNetworkSourceImpl @Inject constructor(
    private val service: CurrencyService,
) : CurrencyNetworkSource {
    override suspend fun getLatest(base: String): Response<LatestRatesResponse> {
        val response = service.getLatest()

        Log.i("CurrencyNetworkSourceImpl", "getLatest: ${response.raw()}")

        return if (response.isSuccessful) {
            Response.Success(response.body()!!)
        } else {
            Response.Error("An Error occurred")
        }
    }

    // Time series endpoint isn't free for fixer API so we use dummy data
    override suspend fun getRateForLast30(
        from: String,
        to: String
    ): Response<TimeSeriesResponse> {
//        val response = service.getTimeSeries(
//            base = from,
//            symbols = to,
//            startDate = LocalDate.now().minusDays(30).format(),
//            endDate = LocalDate.now().format()
//        )
//
//        Log.i("CurrencyNetworkSourceImpl", "getRateForLast30: ${response.raw()}")
//
//        return if (response.isSuccessful) {
//            Response.Success(response.body()!!)
//        } else {
//            Response.Error("An Error occurred")
//        }
        val response: TimeSeriesResponse = Json.decodeFromString(ThirtyDaysResponse)
        return Response.Success(response)
    }

    override suspend fun getRateForLast90(
        from: String,
        to: String
    ): Response<TimeSeriesResponse> {
//        val response = service.getTimeSeries(
//            base = from,
//            symbols = to,
//            startDate = LocalDate.now().minusDays(90).format(),
//            endDate = LocalDate.now().format()
//        )
//
//        Log.i("CurrencyNetworkSourceImpl", "getRateForLast90: ${response.raw()}")
//
//        return if (response.isSuccessful) {
//            Response.Success(response.body()!!)
//        } else {
//            Response.Error("An Error occurred")
//        }
        val response: TimeSeriesResponse = Json.decodeFromString(NinetyDaysResponse)
        return Response.Success(response)
    }
}