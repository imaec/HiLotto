package com.imaec.domain.model

import java.io.Serializable

data class FitNumberDto(
    var listFitNumber: ArrayList<Int> = ArrayList(),
    var numberBonus: Int = 0,
    var rank: Int = 0,
    var round: String = "0회",
    var isFitNo1: Boolean = false,
    var isFitNo2: Boolean = false,
    var isFitNo3: Boolean = false,
    var isFitNo4: Boolean = false,
    var isFitNo5: Boolean = false,
    var isFitNo6: Boolean = false,
    var listIsFitBonus: ArrayList<Boolean> = ArrayList()
) : Serializable
