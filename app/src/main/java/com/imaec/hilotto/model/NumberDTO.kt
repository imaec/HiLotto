package com.imaec.hilotto.model

data class NumberDTO(
    val no: String,
    val isContinue: Boolean = false,
    val continuesList: List<String> = emptyList()
)
