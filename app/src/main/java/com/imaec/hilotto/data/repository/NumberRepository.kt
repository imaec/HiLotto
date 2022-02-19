package com.imaec.hilotto.data.repository

import com.imaec.hilotto.room.entity.NumberEntity

interface NumberRepository {

    suspend fun selectByNumbers(entity: NumberEntity): Int

    suspend fun selectAll(): List<NumberEntity>?

    suspend fun insert(entity: NumberEntity)

    suspend fun insertAll(entities: List<NumberEntity>)

    suspend fun update(entity: NumberEntity): Boolean

    suspend fun delete(entity: NumberEntity)
}
