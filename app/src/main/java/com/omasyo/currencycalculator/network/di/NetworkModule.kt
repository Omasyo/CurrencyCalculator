package com.omasyo.currencycalculator.network.di

import com.omasyo.currencycalculator.network.CurrencyNetworkSource
import com.omasyo.currencycalculator.network.CurrencyNetworkSourceImpl
import com.omasyo.currencycalculator.network.CurrencyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .baseUrl("https://data.fixer.io/api/")
        .addConverterFactory(
            json.asConverterFactory(
                "application/json; charset=UTF8".toMediaType()
            )
        )
        .client(client)
        .build()

    @Provides
    @Singleton
    fun providesCurrencyService(
        retrofit: Retrofit
    ): CurrencyService = retrofit.create(CurrencyService::class.java)

    @Provides
    @Singleton
    fun provideCurrencyNetworkSource(service: CurrencyService): CurrencyNetworkSource =
        CurrencyNetworkSourceImpl(service)
}