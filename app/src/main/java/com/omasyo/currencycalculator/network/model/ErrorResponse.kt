package com.omasyo.currencycalculator.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerialName("error")
    val error: Error,
    @SerialName("success")
    val success: Boolean
)

@Serializable
data class Error(
    @SerialName("code")
    val code: Int,
    @SerialName("info")
    val info: String
)