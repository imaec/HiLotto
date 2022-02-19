package com.imaec.hilotto.domain.usecase.firebase

import com.imaec.hilotto.data.repository.FirebaseRepository
import com.imaec.hilotto.di.IoDispatcher
import com.imaec.hilotto.domain.UseCase
import com.imaec.hilotto.model.LottoDTO
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SetLottoListUseCase @Inject constructor(
    private val repository: FirebaseRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<List<LottoDTO>, Unit>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: List<LottoDTO>) = repository.setLottoList(parameters)
}
