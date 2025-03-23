package com.omasyo.currencycalculator.data

import com.omasyo.currencycalculator.common.Response
import com.omasyo.currencycalculator.common.formatToShortDate
import com.omasyo.currencycalculator.common.map
import com.omasyo.currencycalculator.database.ConversionLocalSource
import com.omasyo.currencycalculator.database.conversionhistory.ConversionHistory
import com.omasyo.currencycalculator.database.conversionrate.ConversionRate
import com.omasyo.currencycalculator.model.ChartPoint
import com.omasyo.currencycalculator.network.CurrencyNetworkSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConversionRepositoryImpl @Inject constructor(
    private val networkSource: CurrencyNetworkSource,
    private val localSource: ConversionLocalSource,
    private val dispatcher: CoroutineDispatcher
) : ConversionRepository {
    override fun getCurrencies(base: String): Flow<List<String>> = flow {
        coroutineScope {
            launch(dispatcher) {
                when (val response = networkSource.getLatest(base)) {
                    is Response.Error -> Unit
                    is Response.Success -> {
                        localSource.clearConversionRates()

                        val baseCurrency = response.content.base
                        val data = response.content.rates.map { (currency, rate) ->
                            ConversionRate(
                                currency = currency,
                                rate = rate
                            )
                        }
                        localSource.clearConversionRates()
                        localSource.updateConversionRates(
                            listOf(
                                ConversionRate(
                                    currency = baseCurrency,
                                    rate = 1.0
                                )
                            )
                        )
                        localSource.updateConversionRates(data.filterNot { it.currency == baseCurrency })
                    }
                }
            }
            emitAll(localSource.getCurrencies().flowOn(dispatcher))

        }
    }

    override fun getBaseCurrency(): Flow<String?> {
        return localSource.getBaseCurrency().flowOn(dispatcher)
    }

    override fun getConversionRate(currency: String): Flow<Double?> {
        return localSource.getConversionRate(currency).flowOn(dispatcher)
    }

    override suspend fun getHistory(): Flow<List<ConversionHistory>> {
        return localSource.getHistory().flowOn(dispatcher)
    }

    override suspend fun createConversionHistory(data: ConversionHistory) =
        withContext(dispatcher) {
            localSource.insertConversion(data)
        }

    override suspend fun getRateForLast30(from: String, to: String): Response<List<ChartPoint>> {
        return withContext(dispatcher) {
            networkSource.getRateForLast30(from, to).map { response ->
                response.rates.map { (date, rate) ->
                    ChartPoint(date.formatToShortDate(), rate.values.first())
                }
            }
        }
    }

    override suspend fun getRateForLast90(from: String, to: String): Response<List<ChartPoint>> {
        return withContext(dispatcher) {
            networkSource.getRateForLast90(from, to).map { response ->
                response.rates.map { (date, rate) ->
                    ChartPoint(date.formatToShortDate(), rate.values.first())
                }
            }
        }
    }
}