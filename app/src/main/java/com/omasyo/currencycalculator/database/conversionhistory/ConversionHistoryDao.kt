package com.omasyo.currencycalculator.database.conversionhistory

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ConversionHistoryDao {
    @Query("SELECT * FROM conversion_history")
    fun getHistory(): Flow<List<ConversionHistory>>

    @Insert
    fun insert(data: ConversionHistory)
}