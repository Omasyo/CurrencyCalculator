package com.omasyo.currencycalculator.database.conversionrate

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversion_rate")
data class ConversionRate(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val currency: String,
    val rate: Double,
)
