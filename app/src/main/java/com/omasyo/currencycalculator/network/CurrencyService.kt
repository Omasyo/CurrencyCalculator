package com.omasyo.currencycalculator.network

import com.omasyo.currencycalculator.network.model.LatestRatesResponse
import com.omasyo.currencycalculator.network.model.TimeSeriesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "be7fb90c3f5d411969243ea1a3a36da3"

interface CurrencyService {
    @GET("latest?access_key=$API_KEY")
    suspend fun getLatest(
//        @Query("base") base: String,
    ): Response<LatestRatesResponse>

    @GET("timeseries?access_key=$API_KEY")
    suspend fun getTimeSeries(
        @Query("base") base: String,
        @Query("symbols") symbols: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
    ): Response<TimeSeriesResponse>
}