package com.imaec.hilotto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.model.OddEvenDTO
import com.imaec.hilotto.ui.adapter.OddEvenAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OddEvenViewModel @Inject constructor() : BaseViewModel() {

    init {
        adapter = OddEvenAdapter()
    }

    private val _statisticsNo = MutableLiveData<Int>(20)
    val statisticsNo: LiveData<Int> get() = _statisticsNo

    private val _listOddEven = MutableLiveData<List<OddEvenDTO>>(ArrayList())
    val listOddEven: LiveData<List<OddEvenDTO>> get() = _listOddEven

    fun setStatisticsNo(no: Int) {
        _statisticsNo.value = no
    }

    private fun getOdd(
        no1: Int,
        no2: Int,
        no3: Int,
        no4: Int,
        no5: Int,
        no6: Int
    ): ArrayList<String> {
        val listOdd = ArrayList<String>()
        if (no1 % 2 != 0) listOdd.add("$no1")
        if (no2 % 2 != 0) listOdd.add("$no2")
        if (no3 % 2 != 0) listOdd.add("$no3")
        if (no4 % 2 != 0) listOdd.add("$no4")
        if (no5 % 2 != 0) listOdd.add("$no5")
        if (no6 % 2 != 0) listOdd.add("$no6")
        return listOdd
    }

    private fun getEven(
        no1: Int,
        no2: Int,
        no3: Int,
        no4: Int,
        no5: Int,
        no6: Int
    ): ArrayList<String> {
        val listEven = ArrayList<String>()
        if (no1 % 2 == 0) listEven.add("$no1")
        if (no2 % 2 == 0) listEven.add("$no2")
        if (no3 % 2 == 0) listEven.add("$no3")
        if (no4 % 2 == 0) listEven.add("$no4")
        if (no5 % 2 == 0) listEven.add("$no5")
        if (no6 % 2 == 0) listEven.add("$no6")
        return listEven
    }

    fun setOddEven(listResult: List<LottoDTO>) {
        val listTemp = ArrayList<OddEvenDTO>()
        listResult.subList(0, statisticsNo.value ?: 20).forEach {
            val listOdd =
                getOdd(it.drwtNo1, it.drwtNo2, it.drwtNo3, it.drwtNo4, it.drwtNo5, it.drwtNo6)
            val listEven =
                getEven(it.drwtNo1, it.drwtNo2, it.drwtNo3, it.drwtNo4, it.drwtNo5, it.drwtNo6)
            val listOddEven = ArrayList<String>().apply {
                addAll(listOdd)
                add(":")
                addAll(listEven)
            }

            val dto = OddEvenDTO(
                "${it.drwNo}회",
                listOdd,
                listEven,
                listOddEven,
                "${listOdd.size} : ${listEven.size}"
            )
            listTemp.add(dto)
        }
        _listOddEven.value = listTemp
    }
}
