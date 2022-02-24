package com.imaec.domain.usecase.firebase

import com.imaec.domain.IoDispatcher
import com.imaec.domain.repository.FirebaseRepository
import com.imaec.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SetWeekUseCase @Inject constructor(
    private val repository: FirebaseRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Int, Unit>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: Int) = repository.setWeek(parameters)
}
