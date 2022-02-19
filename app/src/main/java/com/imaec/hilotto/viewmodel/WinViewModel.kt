package com.imaec.hilotto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.LottoDTO
import java.text.DecimalFormat
import kotlin.math.roundToLong

class WinViewModel : BaseViewModel() {

    private val _statisticsNo = MutableLiveData<Int>(20)
    val statisticsNo: LiveData<Int> get() = _statisticsNo

    private val _winAvgTitle = MutableLiveData<String>("1등 당첨 평균(20회)")
    val winAvgTitle: LiveData<String> get() = _winAvgTitle

    private val _priceTotal = MutableLiveData<Long>(0)
    val priceTotal: LiveData<Long> get() = _priceTotal

    private val _price = MutableLiveData<Long>(0)
    val price: LiveData<Long> get() = _price

    private val _winCount = MutableLiveData<String>("0명")
    val winCount: LiveData<String> get() = _winCount

    private val _winStatisticsTitle = MutableLiveData<String>("1등 당첨 통계(20회)")
    val winStatisticsTitle: LiveData<String> get() = _winStatisticsTitle

    private val _priceMax = MutableLiveData<String>("000회 : 0원")
    val priceMax: LiveData<String> get() = _priceMax

    private val _priceMin = MutableLiveData<String>("000회 : 0원")
    val priceMin: LiveData<String> get() = _priceMin

    private val _winCountMax = MutableLiveData<String>("000회 : 0명")
    val winCountMax: LiveData<String> get() = _winCountMax

    private val _winCountMin = MutableLiveData<String>("000회 : 0명")
    val winCountMin: LiveData<String> get() = _winCountMin

    private fun getWinInfo(list: List<LottoDTO>) {
        var sumPriceTotal: Long = 0
        var sumPrice: Long = 0
        var sumCount: Float = 0f
        list.forEach {
            sumPriceTotal += it.firstAccumamnt
            sumPrice += it.firstWinamnt
            sumCount += it.firstPrzwnerCo
        }
        _priceTotal.value = sumPriceTotal / list.size
        _price.value = sumPrice / list.size
        _winCount.value = "${sumCount / list.size}명"

        val priceMax = list.sortedByDescending { it.firstWinamnt }[0].firstWinamnt
        val priceMaxRound = list.sortedByDescending { it.firstWinamnt }[0].drwNo
        var decimal = DecimalFormat("###,###").format(priceMax)
        _priceMax.value = if (priceMax > 100000000) {
            val unitValue = (priceMax / 100000000.0).roundToLong()
            "${priceMaxRound}회 : $decimal (약 ${unitValue}억)"
        } else {
            "${priceMaxRound}회 : $decimal"
        }
        val priceMin = list.sortedBy { it.firstWinamnt }[0].firstWinamnt
        val priceMinRound = list.sortedBy { it.firstWinamnt }[0].drwNo
        decimal = DecimalFormat("###,###").format(priceMin)
        _priceMin.value = if (priceMin > 100000000) {
            val unitValue = (priceMin / 100000000.0).roundToLong()
            "${priceMinRound}회 : $decimal (약 ${unitValue}억)"
        } else {
            "${priceMinRound}회 : $decimal"
        }

        val countMaxRound = list.sortedByDescending { it.firstPrzwnerCo }[0].drwNo
        _winCountMax.value = "${countMaxRound}회 : ${list.sortedByDescending { it.firstPrzwnerCo }[0].firstPrzwnerCo}명"
        val countMinRound = list.sortedBy { it.firstPrzwnerCo }[0].drwNo
        _winCountMin.value = "${countMinRound}회 : ${list.sortedBy { it.firstPrzwnerCo }[0].firstPrzwnerCo}명"
    }

    fun setStatisticsNo(no: Int) {
        _statisticsNo.value = no
        _winAvgTitle.value = "1등 당첨 평균(${no}회)"
        _winStatisticsTitle.value = "1등 당첨 통계(${no}회)"
    }

    fun setWinInfo(listResult: List<LottoDTO>) {
        val listTemp = ArrayList<LottoDTO>()
        listResult.subList(0, statisticsNo.value ?: 20).forEach {
            listTemp.add(it)
        }

        getWinInfo(listTemp)
    }
}
