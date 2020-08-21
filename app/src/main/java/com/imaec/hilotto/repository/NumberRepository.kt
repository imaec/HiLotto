package com.imaec.hilotto.repository

import com.imaec.hilotto.room.dao.NumberDao
import com.imaec.hilotto.room.entity.NumberEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NumberRepository(
    private val dao: NumberDao
) {

    suspend fun selectByNumbers(entity: NumberEntity): Int {
        var count = 0
        withContext(Dispatchers.IO) {
            count = dao.selectByNumbers(entity.number1, entity.number2, entity.number3, entity.number4, entity.number5, entity.number6)
        }
        return count
    }

    suspend fun selectAll(): List<NumberEntity>? {
        var listResult: List<NumberEntity>? = null
        withContext(Dispatchers.IO) {
            listResult = dao.selectAll()
        }
        return listResult
    }

    suspend fun insert(entity: NumberEntity) {
        withContext(Dispatchers.IO) {
            dao.insert(entity)
        }
    }

    suspend fun update(entity: NumberEntity): Boolean {
        var result = 0
        withContext(Dispatchers.IO) {
            result = dao.update(entity)
        }
        return result > 0
    }

    suspend fun delete(entity: NumberEntity) {
        withContext(Dispatchers.IO) {
            dao.delete(entity)
        }
    }
}