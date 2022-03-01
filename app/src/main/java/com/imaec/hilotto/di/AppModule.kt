package com.imaec.hilotto.di

import com.imaec.hilotto.base.LoadingState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object AppModule {

    @Provides
    fun provideLoading(): LoadingState = LoadingState()
}
