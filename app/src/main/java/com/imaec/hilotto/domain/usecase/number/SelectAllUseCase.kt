package com.imaec.hilotto.domain.usecase.number

import com.imaec.hilotto.data.repository.NumberRepository
import javax.inject.Inject

class SelectAllUseCase @Inject constructor(private val repository: NumberRepository) {

    fun execute() = repository.selectAll()
}
