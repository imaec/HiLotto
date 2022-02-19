package com.imaec.hilotto.data.di

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.imaec.hilotto.data.repository.FirebaseRepository
import com.imaec.hilotto.data.repository.LottoRepository
import com.imaec.hilotto.data.repository.NumberRepository
import com.imaec.hilotto.domain.repository.FirebaseRepositoryImpl
import com.imaec.hilotto.domain.repository.LottoRepositoryImpl
import com.imaec.hilotto.domain.repository.NumberRepositoryImpl
import com.imaec.hilotto.room.dao.NumberDao
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
    fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseReference(firebaseDatabase: FirebaseDatabase): DatabaseReference =
        firebaseDatabase.reference

    @Provides
    @Singleton
    fun provideFirebaseRepository(reference: DatabaseReference): FirebaseRepository =
        FirebaseRepositoryImpl(reference)

    @Provides
    @Singleton
    fun provideLottoRepository(): LottoRepository = LottoRepositoryImpl()

    @Provides
    @Singleton
    fun provideNumberRepository(dao: NumberDao): NumberRepository = NumberRepositoryImpl(dao)
}
