package com.omasyo.currencycalculator.database.conversionhistory

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "conversion_history")
data class ConversionHistory(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "from_currency") val fromCurrency: String,
    @ColumnInfo(name = "from_amount") val fromAmount: Double,
    @ColumnInfo(name = "to_currency") val toCurrency: String,
    @ColumnInfo(name = "to_amount") val toAmount: Double,
    @ColumnInfo(name = "dateTime") val dateTime: LocalDateTime,
)