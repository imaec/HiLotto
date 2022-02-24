package com.imaec.data.entity

import com.imaec.domain.model.LottoDto

data class LottoEntity(
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
) {
    fun toDto() = LottoDto(
        bnusNo = bnusNo,
        drwNo = drwNo,
        drwNoDate = drwNoDate,
        drwtNo1 = drwtNo1,
        drwtNo2 = drwtNo2,
        drwtNo3 = drwtNo3,
        drwtNo4 = drwtNo4,
        drwtNo5 = drwtNo5,
        drwtNo6 = drwtNo6,
        firstAccumamnt = firstAccumamnt,
        firstPrzwnerCo = firstPrzwnerCo,
        firstWinamnt = firstWinamnt,
        returnValue = returnValue,
        totSellamnt = totSellamnt
    )
}
