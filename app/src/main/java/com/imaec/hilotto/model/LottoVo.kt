package com.imaec.hilotto.model

import com.imaec.domain.model.LottoDto
import com.imaec.hilotto.Lotto
import java.io.Serializable
import java.text.DecimalFormat
import kotlin.math.roundToLong

data class LottoVo(
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
) : Serializable {

    companion object {
        fun dtoToVo(dto: LottoDto) = LottoVo(
            bnusNo = dto.bnusNo,
            drwNo = dto.drwNo,
            drwNoDate = dto.drwNoDate,
            drwtNo1 = dto.drwtNo1,
            drwtNo2 = dto.drwtNo2,
            drwtNo3 = dto.drwtNo3,
            drwtNo4 = dto.drwtNo4,
            drwtNo5 = dto.drwtNo5,
            drwtNo6 = dto.drwtNo6,
            firstAccumamnt = dto.firstAccumamnt,
            firstPrzwnerCo = dto.firstPrzwnerCo,
            firstWinamnt = dto.firstWinamnt,
            returnValue = dto.returnValue,
            totSellamnt = dto.totSellamnt
        )
    }

    fun toLatelyResultVo(): LatelyResultVo {
        val prizeTotal = if (firstAccumamnt > 100000000) {
            "${(firstAccumamnt / 100000000.0).roundToLong()}억 원"
        } else {
            "${DecimalFormat("###,###").format(firstAccumamnt)} 원"
        }
        val prizeByOne = if (firstWinamnt > 100000000) {
            "${(firstWinamnt / 100000000.0).roundToLong()}억 원"
        } else {
            "${DecimalFormat("###,###").format(firstWinamnt)} 원"
        }
        return LatelyResultVo(
            round = "${drwNo}회 당첨번호",
            date = drwNoDate,
            no1 = drwtNo1,
            no2 = drwtNo2,
            no3 = drwtNo3,
            no4 = drwtNo4,
            no5 = drwtNo5,
            no6 = drwtNo6,
            noBonus = bnusNo,
            prizeTotal = prizeTotal,
            prizeByOne = prizeByOne,
            winCount = "$firstPrzwnerCo 명",
            noSum = Lotto.getSumAvg(listOf(this)),
            noContinues = Lotto.getSequenceString(
                Lotto.getContinues(
                    drwtNo1,
                    drwtNo2,
                    drwtNo3,
                    drwtNo4,
                    drwtNo5,
                    drwtNo6
                )
            ),
            noOddEven = "홀 ${
            Lotto.getOdd(
                listOf(
                    drwtNo1,
                    drwtNo2,
                    drwtNo3,
                    drwtNo4,
                    drwtNo5,
                    drwtNo6
                )
            ).size
            } / 짝 ${
            Lotto.getEven(
                listOf(
                    drwtNo1,
                    drwtNo2,
                    drwtNo3,
                    drwtNo4,
                    drwtNo5,
                    drwtNo6
                )
            ).size
            }"
        )
    }
}
