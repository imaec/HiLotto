package com.imaec.domain.usecase.firebase

import com.imaec.domain.IoDispatcher
import com.imaec.domain.model.LottoDto
import com.imaec.domain.repository.FirebaseRepository
import com.imaec.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SetLottoListUseCase @Inject constructor(
    private val repository: FirebaseRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<List<LottoDto>, Unit>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: List<LottoDto>) = repository.setLottoList(parameters)
}
