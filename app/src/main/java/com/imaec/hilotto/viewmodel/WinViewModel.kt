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

    private val _priceMax = MutableLiveData<String>().set("000회 : 0원")
    val priceMax: LiveData<String> get() = _priceMax

    private val _priceMin = MutableLiveData<String>().set("000회 : 0원")
    val priceMin: LiveData<String> get() = _priceMin

    private val _winCountMax = MutableLiveData<String>().set("000회 : 0명")
    val winCountMax: LiveData<String> get() = _winCountMax

    private val _winCountMin = MutableLiveData<String>().set("000회 : 0명")
    val winCountMin: LiveData<String> get() = _winCountMin

    private fun getWinInfo(list: List<LottoDTO>) {
        var sumPrice: Long = 0
        var sumPriceTotal: Long = 0
        var sumCount = 0
        list.forEach {
            sumPrice += it.firstAccumamnt
            sumPriceTotal += it.firstWinamnt
            sumCount += it.firstPrzwnerCo
        }
        _price.value = sumPrice / list.size
        _priceTotal.value = sumPriceTotal / list.size
        _winCount.value = "${sumCount / list.size}명"
        _priceMax.value = "${list.sortedByDescending { it.firstAccumamnt }[0].firstAccumamnt}"
        _priceMin.value = "${list.sortedBy { it.firstAccumamnt }[0].firstAccumamnt}"
        _winCountMax.value = "${list.sortedByDescending { it.firstPrzwnerCo }[0].firstPrzwnerCo}"
        _winCountMin.value = "${list.sortedBy { it.firstPrzwnerCo }[0].firstPrzwnerCo}"
    }

    fun setWinInfo(listResult: List<LottoDTO>) {
        val listTemp = ArrayList<LottoDTO>()
        listResult.subList(listResult.size - 21, listResult.size - 1).reversed().forEach {
            listTemp.add(it)
        }

        getWinInfo(listTemp)
    }
}