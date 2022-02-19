package com.imaec.hilotto.domain.usecase.lotto

import com.imaec.hilotto.data.repository.LottoRepository
import com.imaec.hilotto.di.IoDispatcher
import com.imaec.hilotto.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetCurDrwNoUseCase @Inject constructor(
    private val repository: LottoRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Pair<String, (Int) -> Unit>, Unit>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: Pair<String, (Int) -> Unit>) =
        repository.getCurDrwNo(parameters.first, parameters.second)
}
