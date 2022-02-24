package com.imaec.domain.usecase.lotto

import com.imaec.domain.IoDispatcher
import com.imaec.domain.repository.LottoRepository
import com.imaec.domain.NoParamUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetCurDrwNoUseCase @Inject constructor(
    private val repository: LottoRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : NoParamUseCase<Int>(coroutineDispatcher = dispatcher) {

    override suspend fun execute() = repository.getCurDrwNo()
}
