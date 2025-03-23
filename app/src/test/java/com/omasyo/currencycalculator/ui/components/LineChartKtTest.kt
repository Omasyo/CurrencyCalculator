package com.omasyo.currencycalculator.ui.components

import androidx.compose.ui.geometry.Offset
import org.junit.Assert.assertEquals
import org.junit.Test

class LineChartKtTest {
    @Test
    fun getNearestPointTest() {
        val offsets = listOf(
            Offset(0.5f, 1.2f), Offset(1.1f, 1.8f), Offset(2.3f, 0.7f), Offset(3.4f, 2.1f),
            Offset(0.9f, 3.5f), Offset(2.7f, 1.4f), Offset(1.6f, 0.9f), Offset(3.2f, 2.8f),
            Offset(0.4f, 1.9f), Offset(1.8f, 3.3f), Offset(2.5f, 0.5f), Offset(3.0f, 1.7f),
            Offset(1.3f, 2.6f), Offset(0.7f, 3.1f), Offset(2.9f, 0.8f), Offset(3.6f, 1.5f),
            Offset(1.2f, 2.4f), Offset(0.6f, 3.7f), Offset(2.1f, 0.6f), Offset(3.3f, 2.0f)
        )

        assertEquals(offsets.getNearestIndex(Offset(1.4f, 2.7f)), 12)
    }
}