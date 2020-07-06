package com.imaec.hilotto.model

data class LottoDTO(
    var bnusNo: Int,
    var drwNo: Int,
    var drwNoDate: String,
    var drwtNo1: Int,
    var drwtNo2: Int,
    var drwtNo3: Int,
    var drwtNo4: Int,
    var drwtNo5: Int,
    var drwtNo6: Int,
    var firstAccumamnt: Long,
    var firstPrzwnerCo: Int,
    var firstWinamnt: Long,
    var returnValue: String,
    var totSellamnt: Long
)