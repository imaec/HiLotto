package com.imaec.hilotto.model

data class LatelyResultDTO(
    var round: String = "1회 당첨번호",
    var date: String = "0000-00-00",
    var no1: Int = 0,
    var no2: Int = 0,
    var no3: Int = 0,
    var no4: Int = 0,
    var no5: Int = 0,
    var no6: Int = 0,
    var noBonus: Int = 0,
    var prizeTotal: String = "",
    var prizeByOne: String = "",
    var winCount: String = "0명",
    var noSum: Int = 0,
    var noContinues: String = "연속번호 없음",
    var noOddEven: String = ""
)
