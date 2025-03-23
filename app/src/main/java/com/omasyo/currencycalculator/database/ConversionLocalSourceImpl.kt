package com.omasyo.currencycalculator.database

import com.omasyo.currencycalculator.database.conversionhistory.ConversionHistory
import com.omasyo.currencycalculator.database.conversionrate.ConversionRate
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConversionLocalSourceImpl @Inject constructor (
    roomDatabase: AppDatabase,
) : ConversionLocalSource {
    private val conversionRateDao = roomDatabase.conversionRateDao()
    private val conversionHistoryDao = roomDatabase.conversionHistoryDao()

    override fun getCurrencies(): Flow<List<String>> {
        return conversionRateDao.getAllCurrencies()
    }

    override fun getBaseCurrency(): Flow<String?> {
        return conversionRateDao.getBaseCurrency()
    }

    override fun getConversionRate(currency: String): Flow<Double> {
        return conversionRateDao.getConversionRate(currency)
    }

    override fun updateConversionRates(rates: List<ConversionRate>) {
        conversionRateDao.insertAll(rates)
    }

    override fun clearConversionRates() {
        conversionRateDao.clear()
    }

    override suspend fun getHistory(): Flow<List<ConversionHistory>> {
        return conversionHistoryDao.getHistory()
    }

    override fun insertConversion(data: ConversionHistory) {
        conversionHistoryDao.insert(data)
    }
}