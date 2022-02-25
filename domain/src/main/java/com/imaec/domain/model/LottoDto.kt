package com.imaec.domain.model

import java.io.Serializable

data class LottoDto(
    var bnusNo: Int = 0,
    var drwNo: Int = 0,
    var drwNoDate: String = "",
    var drwtNo1: Int = 0,
    var drwtNo2: Int = 0,
    var drwtNo3: Int = 0,
    var drwtNo4: Int = 0,
    var drwtNo5: Int = 0,
    var drwtNo6: Int = 0,
    var firstAccumamnt: Long = 0,
    var firstPrzwnerCo: Int = 0,
    var firstWinamnt: Long = 0,
    var returnValue: String = "",
    var totSellamnt: Long = 0
) : Serializable
