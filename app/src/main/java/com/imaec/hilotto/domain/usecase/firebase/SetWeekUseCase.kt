package com.imaec.hilotto.domain.usecase.firebase

import com.imaec.hilotto.data.repository.FirebaseRepository
import com.imaec.hilotto.di.IoDispatcher
import com.imaec.hilotto.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SetWeekUseCase @Inject constructor(
    private val repository: FirebaseRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Int, Unit>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: Int) = repository.setWeek(parameters)
}
