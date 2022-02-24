package com.imaec.domain.usecase.number

import com.imaec.domain.IoDispatcher
import com.imaec.domain.repository.NumberRepository
import com.imaec.domain.UseCase
import com.imaec.domain.model.MyNumberDto
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SelectByNumbersUseCase @Inject constructor(
    private val repository: NumberRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<MyNumberDto, Int>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: MyNumberDto) =
        repository.selectByNumbers(parameters)
}
