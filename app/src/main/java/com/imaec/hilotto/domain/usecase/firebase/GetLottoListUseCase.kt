package com.imaec.hilotto.domain.usecase.firebase

import com.imaec.hilotto.data.repository.FirebaseRepository
import com.imaec.hilotto.di.IoDispatcher
import com.imaec.hilotto.domain.UseCase
import com.imaec.hilotto.model.LottoDTO
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetLottoListUseCase @Inject constructor(
    private val repository: FirebaseRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<(List<LottoDTO>) -> Unit, Unit>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: (List<LottoDTO>) -> Unit) =
        repository.getLottoList(parameters)
}
