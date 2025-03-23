package com.omasyo.currencycalculator.data.di

import com.omasyo.currencycalculator.data.ConversionRepository
import com.omasyo.currencycalculator.data.ConversionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindConversionRepository(conversionRepository: ConversionRepositoryImpl): ConversionRepository
}