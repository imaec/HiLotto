package com.imaec.hilotto.model

data class WinDTO(
    var price: Long = 0,
    var priceTotal: Long = 0,
    var winCount: String = "0명",
    var priceMax: String = "000회 : 0원",
    var priceMin: String = "000회 : 0원",
    var winCountMax: String = "000회 : 0명",
    var winCountMin: String = "000회 : 0명"
)
