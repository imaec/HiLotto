package com.imaec.hilotto.model

data class NumberVo(
    val no: String,
    val isContinue: Boolean = false,
    val continuesList: List<String> = emptyList()
)
