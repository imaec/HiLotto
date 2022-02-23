package com.imaec.hilotto.domain.usecase.lotto

import com.imaec.hilotto.data.repository.LottoRepository
import com.imaec.hilotto.di.IoDispatcher
import com.imaec.hilotto.domain.UseCase
import com.imaec.hilotto.model.StoreDTO
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetStoreUseCase @Inject constructor(
    private val repository: LottoRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Int, List<StoreDTO>>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: Int) = repository.getStore(parameters)
}
