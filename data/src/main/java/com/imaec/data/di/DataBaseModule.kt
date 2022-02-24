package com.imaec.data.di

import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.preferencesDataStore
import com.imaec.data.db.AppDatabase
import com.imaec.data.db.dao.NumberDao
import com.imaec.data.prefs.DataStoreStorageImpl
import com.imaec.data.prefs.DataStoreStorageImpl.Companion.PREFS_NAME
import com.imaec.domain.prefs.DataStoreStorage
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

    private val Context.dataStore by preferencesDataStore(
        name = PREFS_NAME,
        produceMigrations = {
            listOf(
                SharedPreferencesMigration(
                    it,
                    PREFS_NAME
                )
            )
        }
    )

    @Singleton
    @Provides
    fun providePreferenceStorage(@ApplicationContext context: Context): DataStoreStorage =
        DataStoreStorageImpl(context.dataStore)
}
