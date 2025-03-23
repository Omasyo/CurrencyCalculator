package com.omasyo.currencycalculator.database.conversionrate

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ConversionRateDao {
    @Query("DELETE FROM conversion_rate")
    fun clear()

    @Insert
    fun insertAll(rates: List<ConversionRate>)

    @Query("SELECT currency FROM conversion_rate")
    fun getAllCurrencies(): Flow<List<String>>

    @Query("SELECT currency FROM conversion_rate LIMIT 1")
    fun getBaseCurrency(): Flow<String?>

    @Query("SELECT rate FROM conversion_rate WHERE currency = :currency")
    fun getConversionRate(currency: String): Flow<Double?>

    @Query("SELECT rate FROM conversion_rate WHERE currency = :currency")
    fun getRate(currency: String): Flow<Double>
}
