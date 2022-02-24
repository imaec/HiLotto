package com.imaec.domain.repository

import androidx.lifecycle.LiveData
import com.imaec.domain.model.MyNumberDto

interface NumberRepository {

    suspend fun selectByNumbers(dto: MyNumberDto): Int

    fun selectAll(): LiveData<List<MyNumberDto>>

    fun selectAllList(): List<MyNumberDto>

    suspend fun insert(dto: MyNumberDto)

    suspend fun insertAll(dtoList: List<MyNumberDto>)

    suspend fun update(dto: MyNumberDto): Boolean

    suspend fun delete(dto: MyNumberDto)

    suspend fun deleteById(id: Long)
}
