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

    override fun selectAll() = dao.selectAll()

    override suspend fun insert(entity: NumberEntity) {
        dao.insert(entity)
    }

    override suspend fun insertAll(entities: List<NumberEntity>) {
        dao.insertAll(entities)
    }

    override suspend fun update(entity: NumberEntity): Boolean = dao.update(entity) > 0

    override suspend fun delete(entity: NumberEntity) {
        dao.delete(entity)
    }

    override suspend fun deleteById(id: Long) {
        dao.deleteById(id)
    }
}
