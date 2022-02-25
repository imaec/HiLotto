package com.imaec.data.entity

import com.imaec.domain.model.LottoDto

data class LottoEntity(
    val bnusNo: Int = 0,
    val drwNo: Int = 0,
    val drwNoDate: String = "",
    val drwtNo1: Int = 0,
    val drwtNo2: Int = 0,
    val drwtNo3: Int = 0,
    val drwtNo4: Int = 0,
    val drwtNo5: Int = 0,
    val drwtNo6: Int = 0,
    val firstAccumamnt: Long = 0,
    val firstPrzwnerCo: Int = 0,
    val firstWinamnt: Long = 0,
    val returnValue: String = "",
    val totSellamnt: Long = 0
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
