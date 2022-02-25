package com.imaec.domain.model

import java.io.Serializable

data class LottoDto(
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
) : Serializable
