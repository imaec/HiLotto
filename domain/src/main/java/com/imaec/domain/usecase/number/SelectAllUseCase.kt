package com.imaec.domain.usecase.number

import com.imaec.domain.repository.NumberRepository
import javax.inject.Inject

class SelectAllUseCase @Inject constructor(private val repository: NumberRepository) {

    fun execute() = repository.selectAll()
}
