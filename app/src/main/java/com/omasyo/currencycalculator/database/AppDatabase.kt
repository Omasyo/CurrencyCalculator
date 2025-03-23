package com.omasyo.currencycalculator.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.omasyo.currencycalculator.database.conversionhistory.ConversionHistory
import com.omasyo.currencycalculator.database.conversionhistory.ConversionHistoryDao
import com.omasyo.currencycalculator.database.conversionrate.ConversionRate
import com.omasyo.currencycalculator.database.conversionrate.ConversionRateDao

@Database(
    entities = [
        ConversionHistory::class,
        ConversionRate::class,
    ],
    version = 1,
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun conversionHistoryDao(): ConversionHistoryDao

    abstract fun conversionRateDao(): ConversionRateDao
}