package com.imaec.hilotto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.LottoDTO

class WinViewModel : BaseViewModel() {

    private val _price = MutableLiveData<Long>().set(0)
    val price: LiveData<Long> get() = _price

    private val _priceTotal = MutableLiveData<Long>().set(0)
    val priceTotal: LiveData<Long> get() = _priceTotal

    private val _winCount = MutableLiveData<String>().set("0명")
    val winCount: LiveData<String> get() = _winCount

    private fun getPriceAvg(list: List<LottoDTO>): Long {
        var sum: Long = 0
        list.forEach {
            sum += it.firstWinamnt
        }
        return sum / list.size
    }

    private fun getPriceTotalAvg(list: List<LottoDTO>): Long {
        var sum: Long = 0
        list.forEach {
            sum += it.firstAccumamnt
        }
        return sum / list.size
    }

    private fun getWinCount(list: List<LottoDTO>): String {
        var sum = 0
        list.forEach {
            sum += it.firstPrzwnerCo
        }
        return "${sum / list.size}명"
    }

    fun setWinInfo(listResult: List<LottoDTO>) {
        val listTemp = ArrayList<LottoDTO>()
        listResult.subList(listResult.size - 21, listResult.size - 1).reversed().forEach {
            listTemp.add(it)
        }

        _price.value = getPriceAvg(listTemp)
        _priceTotal.value = getPriceTotalAvg(listTemp)
        _winCount.value = getWinCount(listTemp)
    }
}