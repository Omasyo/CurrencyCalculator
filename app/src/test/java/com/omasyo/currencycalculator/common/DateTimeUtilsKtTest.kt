package com.omasyo.currencycalculator.common

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class DateTimeUtilsKtTest {

    @Test
    fun formatToLongDateTest() {
        val date = LocalDate.of(2025, 2, 24)
        val dateTime = LocalDateTime.of(date, LocalTime.now())
        assertEquals(dateTime.formatToLongDate(), "2025-02-24")
    }

    @Test
    fun formatToShortDateTest() {
        val date = LocalDate.of(2025, 2, 24)
        assertEquals(date.formatToShortDate(), "24 Feb")
    }
}