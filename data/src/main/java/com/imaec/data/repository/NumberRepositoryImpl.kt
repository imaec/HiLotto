package com.imaec.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.imaec.data.db.dao.NumberDao
import com.imaec.data.entity.NumberEntity.Companion.fromDto
import com.imaec.data.entity.NumberEntity.Companion.toDto
import com.imaec.domain.model.MyNumberDto
import com.imaec.domain.repository.NumberRepository

class NumberRepositoryImpl(
    private val dao: NumberDao
) : NumberRepository {

    override suspend fun selectByNumbers(dto: MyNumberDto): Int {
        return dao.selectByNumbers(
            dto.number1,
            dto.number2,
            dto.number3,
            dto.number4,
            dto.number5,
            dto.number6
        )
    }

    override fun selectAll(): LiveData<List<MyNumberDto>> = Transformations.map(dao.selectAll()) {
        it.map(::toDto)
    }

    override fun selectAllList() = dao.selectAllList().map(::toDto)

    override suspend fun insert(dto: MyNumberDto) {
        dao.insert(fromDto(dto))
    }

    override suspend fun insertAll(dtoList: List<MyNumberDto>) {
        dao.insertAll(dtoList.map(::fromDto))
    }

    override suspend fun update(dto: MyNumberDto): Boolean = dao.update(fromDto(dto)) > 0

    override suspend fun delete(dto: MyNumberDto) {
        dao.delete(fromDto(dto))
    }

    override suspend fun deleteById(id: Long) {
        dao.deleteById(id)
    }
}
