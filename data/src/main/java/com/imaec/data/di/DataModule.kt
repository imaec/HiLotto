package com.imaec.data.di

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.imaec.data.api.LottoService
import com.imaec.data.db.dao.NumberDao
import com.imaec.data.repository.FirebaseRepositoryImpl
import com.imaec.data.repository.LottoRepositoryImpl
import com.imaec.data.repository.NumberRepositoryImpl
import com.imaec.domain.repository.FirebaseRepository
import com.imaec.domain.repository.LottoRepository
import com.imaec.domain.repository.NumberRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): DatabaseReference = Firebase.database.reference

    @Provides
    @Singleton
    fun provideFirebaseRepository(reference: DatabaseReference): FirebaseRepository =
        FirebaseRepositoryImpl(reference)

    @Provides
    @Singleton
    fun provideLottoRepository(service: LottoService): LottoRepository =
        LottoRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideNumberRepository(dao: NumberDao): NumberRepository = NumberRepositoryImpl(dao)
}
