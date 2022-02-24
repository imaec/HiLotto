package com.imaec.data.di

import com.imaec.data.api.Host
import com.imaec.data.di.NetworkCoreModule.HOST_SAMPLE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkHostModule {

    @Provides
    @Singleton
    @Named(HOST_SAMPLE)
    fun provideHost() = Host.SAMPLE_URL
}
