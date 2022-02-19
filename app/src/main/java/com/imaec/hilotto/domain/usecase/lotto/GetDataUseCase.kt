package com.imaec.hilotto.domain.usecase.lotto

import com.imaec.hilotto.data.repository.LottoRepository
import com.imaec.hilotto.di.IoDispatcher
import com.imaec.hilotto.domain.UseCase
import com.imaec.hilotto.model.LottoDTO
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetDataUseCase @Inject constructor(
    private val repository: LottoRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Triple<Int, (LottoDTO) -> Unit, () -> Unit>, Unit>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: Triple<Int, (LottoDTO) -> Unit, () -> Unit>) =
        repository.getData(parameters.first, parameters.second, parameters.third)
}
