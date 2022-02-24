package com.imaec.domain.usecase.number

import com.imaec.domain.IoDispatcher
import com.imaec.domain.repository.NumberRepository
import com.imaec.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DeleteByIdUseCase @Inject constructor(
    private val repository: NumberRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Long, Unit>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: Long) =
        repository.deleteById(parameters)
}
