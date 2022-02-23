package com.imaec.hilotto.domain.usecase.lotto

import com.imaec.hilotto.data.repository.LottoRepository
import com.imaec.hilotto.di.IoDispatcher
import com.imaec.hilotto.domain.NoParamUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetCurDrwNoUseCase @Inject constructor(
    private val repository: LottoRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : NoParamUseCase<Int>(coroutineDispatcher = dispatcher) {

    override suspend fun execute() = repository.getCurDrwNo()
}
