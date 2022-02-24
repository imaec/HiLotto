package com.imaec.data.di

import android.content.Context
import com.imaec.data.db.AppDatabase
import com.imaec.data.db.dao.NumberDao
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
