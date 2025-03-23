package com.omasyo.currencycalculator.network.model

import com.omasyo.currencycalculator.network.serializer.DateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class TimeSeriesResponse(
    @SerialName("base")
    val base: String,
    @SerialName("end_date")
    val endDate: String,

    @SerialName("rates")
    val rates: Map<@Serializable(DateSerializer::class) LocalDate, Map<String, Double>>,

    @SerialName("start_date")
    val startDate: String,
    @SerialName("success")
    val success: Boolean,
    @SerialName("timeseries")
    val timeseries: Boolean
)