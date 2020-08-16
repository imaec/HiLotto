package com.imaec.hilotto.repository

import com.imaec.hilotto.room.dao.NumberDao
import com.imaec.hilotto.room.entity.NumberEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NumberRepository(
    private val dao: NumberDao
) {

    suspend fun insert(entity: NumberEntity) {
        withContext(Dispatchers.IO) {
            dao.insert(entity)
        }
    }
}