package com.imaec.domain.usecase.number

import com.imaec.domain.IoDispatcher
import com.imaec.domain.repository.NumberRepository
import com.imaec.domain.NoParamUseCase
import com.imaec.domain.model.MyNumberDto
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SelectAllListUseCase @Inject constructor(
    private val repository: NumberRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : NoParamUseCase<List<MyNumberDto>>(dispatcher) {

    override suspend fun execute(): List<MyNumberDto> = repository.selectAllList()
}
