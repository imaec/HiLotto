package com.imaec.hilotto.model

data class FitNumberDTO(
    var listFitNumber: ArrayList<Int> = ArrayList(),
    var numberBonus: Int = 0,
    var rank: Int = 0,
    var round: String = "0회"
)