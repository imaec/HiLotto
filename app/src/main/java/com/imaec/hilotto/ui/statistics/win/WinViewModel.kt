package com.imaec.hilotto.ui.statistics.win

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.LottoDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.DecimalFormat
import javax.inject.Inject
import kotlin.math.roundToLong

@HiltViewModel
class WinViewModel @Inject constructor() : BaseViewModel() {

    private val _statisticsNo = MutableLiveData(20)
    val statisticsNo: LiveData<Int> get() = _statisticsNo

    private val _winAvgTitle = MutableLiveData("1등 당첨 평균(20회)")
    val winAvgTitle: LiveData<String> get() = _winAvgTitle

    private val _priceTotal = MutableLiveData<Long>(0)
    val priceTotal: LiveData<Long> get() = _priceTotal

    private val _price = MutableLiveData<Long>(0)
    val price: LiveData<Long> get() = _price

    private val _winCount = MutableLiveData("0명")
    val winCount: LiveData<String> get() = _winCount

    private val _winStatisticsTitle = MutableLiveData("1등 당첨 통계(20회)")
    val winStatisticsTitle: LiveData<String> get() = _winStatisticsTitle

    private val _priceMax = MutableLiveData("000회 : 0원")
    val priceMax: LiveData<String> get() = _priceMax

    private val _priceMin = MutableLiveData("000회 : 0원")
    val priceMin: LiveData<String> get() = _priceMin

    private val _winCountMax = MutableLiveData("000회 : 0명")
    val winCountMax: LiveData<String> get() = _winCountMax

    private val _winCountMin = MutableLiveData("000회 : 0명")
    val winCountMin: LiveData<String> get() = _winCountMin

    fun setStatisticsNo(no: Int) {
        _statisticsNo.value = no
        _winAvgTitle.value = "1등 당첨 평균(${no}회)"
        _winStatisticsTitle.value = "1등 당첨 통계(${no}회)"
    }

    fun setWinInfo(lottoList: List<LottoDTO>) {
        val list = lottoList.subList(0, statisticsNo.value ?: 20)

        // 총 당첨금, 1등 당첨금, 1등 당첨자 수
        var sumPriceTotal: Long = 0
        var sumPrice: Long = 0
        var sumCount = 0f
        list.forEach {
            sumPriceTotal += it.firstAccumamnt
            sumPrice += it.firstWinamnt
            sumCount += it.firstPrzwnerCo
        }
        _priceTotal.value = sumPriceTotal / list.size
        _price.value = sumPrice / list.size
        _winCount.value = "${sumCount / list.size}명"

        // 최대 당첨금, 회차
        val priceMax = list.sortedByDescending { it.firstWinamnt }[0].firstWinamnt
        val priceMaxRound = list.sortedByDescending { it.firstWinamnt }[0].drwNo
        var decimal = DecimalFormat("###,###").format(priceMax)
        _priceMax.value = if (priceMax > 100000000) {
            val unitValue = (priceMax / 100000000.0).roundToLong()
            "${priceMaxRound}회 : $decimal (약 ${unitValue}억)"
        } else {
            "${priceMaxRound}회 : $decimal"
        }

        // 최소 당첨금, 회차
        val priceMin = list.sortedBy { it.firstWinamnt }[0].firstWinamnt
        val priceMinRound = list.sortedBy { it.firstWinamnt }[0].drwNo
        decimal = DecimalFormat("###,###").format(priceMin)
        _priceMin.value = if (priceMin > 100000000) {
            val unitValue = (priceMin / 100000000.0).roundToLong()
            "${priceMinRound}회 : $decimal (약 ${unitValue}억)"
        } else {
            "${priceMinRound}회 : $decimal"
        }

        // 최대 당첨자 수, 회차
        val countMaxRound = list.sortedByDescending { it.firstPrzwnerCo }[0].drwNo
        _winCountMax.value =
            "${countMaxRound}회 : " +
            "${list.sortedByDescending { it.firstPrzwnerCo }[0].firstPrzwnerCo}명"

        // 최소 당첨자 수, 회차
        val countMinRound = list.sortedBy { it.firstPrzwnerCo }[0].drwNo
        _winCountMin.value =
            "${countMinRound}회 : ${list.sortedBy { it.firstPrzwnerCo }[0].firstPrzwnerCo}명"
    }
}
