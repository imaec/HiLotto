package com.imaec.hilotto.domain.usecase.number

import com.imaec.hilotto.data.repository.NumberRepository
import com.imaec.hilotto.di.IoDispatcher
import com.imaec.hilotto.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DeleteByIdUseCase @Inject constructor(
    private val repository: NumberRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Long, Unit>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: Long) =
        repository.deleteById(parameters)
}
