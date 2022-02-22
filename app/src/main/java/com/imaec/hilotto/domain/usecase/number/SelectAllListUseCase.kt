package com.imaec.hilotto.domain.usecase.number

import com.imaec.hilotto.data.repository.NumberRepository
import com.imaec.hilotto.di.IoDispatcher
import com.imaec.hilotto.domain.NoParamUseCase
import com.imaec.hilotto.room.entity.NumberEntity
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SelectAllListUseCase @Inject constructor(
    private val repository: NumberRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : NoParamUseCase<List<NumberEntity>>(dispatcher) {

    override suspend fun execute(): List<NumberEntity> = repository.selectAllList()
}
