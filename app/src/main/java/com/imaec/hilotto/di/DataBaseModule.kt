package com.imaec.hilotto.di

import android.content.Context
import com.imaec.hilotto.room.AppDatabase
import com.imaec.hilotto.room.dao.NumberDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideAppDataBase(
        @ApplicationContext context: Context
    ): AppDatabase = AppDatabase.buildDatabase(context)

    @Singleton
    @Provides
    fun provideNumberDao(
        database: AppDatabase
    ): NumberDao = database.numberDao()
}
