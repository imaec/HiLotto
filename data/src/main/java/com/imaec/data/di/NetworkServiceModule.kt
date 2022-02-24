package com.imaec.data.di

import com.imaec.data.api.LottoService
import com.imaec.data.di.NetworkCoreModule.HOST_SAMPLE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkServiceModule {

    @Provides
    @Singleton
    fun provideService(
        @Named(HOST_SAMPLE) retrofitBuilder: Retrofit.Builder
    ): LottoService = retrofitBuilder.build().create(LottoService::class.java)
}
