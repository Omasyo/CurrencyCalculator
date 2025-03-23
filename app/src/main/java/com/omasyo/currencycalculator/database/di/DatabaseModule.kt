package com.omasyo.currencycalculator.database.di

import android.content.Context
import androidx.room.Room
import com.omasyo.currencycalculator.database.AppDatabase
import com.omasyo.currencycalculator.database.ConversionLocalSource
import com.omasyo.currencycalculator.database.ConversionLocalSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "currency_calculator")
            .build()

    @Provides
    @Singleton
    fun provideConversionLocalSource(
        database: AppDatabase,
    ): ConversionLocalSource = ConversionLocalSourceImpl(database)
}