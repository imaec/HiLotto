package com.imaec.hilotto.domain.repository

import com.imaec.hilotto.data.repository.NumberRepository
import com.imaec.hilotto.room.dao.NumberDao
import com.imaec.hilotto.room.entity.NumberEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NumberRepositoryImpl(
    private val dao: NumberDao
) : NumberRepository {

    override suspend fun selectByNumbers(entity: NumberEntity): Int {
        var count = 0
        withContext(Dispatchers.IO) {
            count = dao.selectByNumbers(
                entity.number1,
                entity.number2,
                entity.number3,
                entity.number4,
                entity.number5,
                entity.number6
            )
        }
        return count
    }

    override suspend fun selectAll(): List<NumberEntity>? {
        var listResult: List<NumberEntity>? = null
        withContext(Dispatchers.IO) {
            listResult = dao.selectAll()
        }
        return listResult
    }

    override suspend fun insert(entity: NumberEntity) {
        withContext(Dispatchers.IO) {
            dao.insert(entity)
        }
    }

    override suspend fun insertAll(entities: List<NumberEntity>) {
        withContext(Dispatchers.IO) {
            dao.insertAll(entities)
        }
    }

    override suspend fun update(entity: NumberEntity): Boolean {
        var result = 0
        withContext(Dispatchers.IO) {
            result = dao.update(entity)
        }
        return result > 0
    }

    override suspend fun delete(entity: NumberEntity) {
        withContext(Dispatchers.IO) {
            dao.delete(entity)
        }
    }
}
