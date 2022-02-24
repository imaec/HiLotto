package com.imaec.domain.usecase.lotto

import com.imaec.domain.IoDispatcher
import com.imaec.domain.model.LottoDto
import com.imaec.domain.repository.LottoRepository
import com.imaec.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetDataUseCase @Inject constructor(
    private val repository: LottoRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Triple<Int, (LottoDto) -> Unit, () -> Unit>, Unit>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: Triple<Int, (LottoDto) -> Unit, () -> Unit>) =
        repository.getData(parameters.first, parameters.second, parameters.third)
}
