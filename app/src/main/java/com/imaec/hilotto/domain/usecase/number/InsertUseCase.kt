package com.imaec.hilotto.domain.usecase.number

import com.imaec.hilotto.data.repository.NumberRepository
import com.imaec.hilotto.di.IoDispatcher
import com.imaec.hilotto.domain.UseCase
import com.imaec.hilotto.room.entity.NumberEntity
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class InsertUseCase @Inject constructor(
    private val repository: NumberRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<NumberEntity, Unit>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: NumberEntity) =
        repository.insert(parameters)
}
