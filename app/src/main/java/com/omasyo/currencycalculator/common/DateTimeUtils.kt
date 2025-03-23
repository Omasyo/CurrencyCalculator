package com.omasyo.currencycalculator.common

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.formatToLongDate(): String {
    return format(DateTimeFormatter.ofPattern("u-MM-dd"))
}

fun LocalDate.formatToShortDate(): String {
    return format(DateTimeFormatter.ofPattern("d LLL"))
}