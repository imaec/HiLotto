package com.imaec.hilotto.ui.statistics.oddeven

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

    private val _statisticsNo = MutableLiveData(20)
    val statisticsNo: LiveData<Int> get() = _statisticsNo

    private val _oddEvenList = MutableLiveData<List<OddEvenDTO>>(ArrayList())
    val oddEvenList: LiveData<List<OddEvenDTO>> get() = _oddEvenList

    fun setStatisticsNo(no: Int) {
        _statisticsNo.value = no
    }

    fun setOddEven(lottoList: List<LottoDTO>) {
        val tempList = ArrayList<OddEvenDTO>()
        lottoList.subList(0, statisticsNo.value ?: 20).forEach {
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
                no = "${it.drwNo}회",
                oddList = listOdd,
                evenList = listEven,
                oddEvenList = listOddEven,
                content = "${listOdd.size} : ${listEven.size}"
            )
            tempList.add(dto)
        }
        _oddEvenList.value = tempList
    }

    private fun getOdd(vararg no: Int): ArrayList<String> {
        val listOdd = ArrayList<String>()
        if (no[0] % 2 != 0) listOdd.add("${no[0]}")
        if (no[1] % 2 != 0) listOdd.add("${no[1]}")
        if (no[2] % 2 != 0) listOdd.add("${no[2]}")
        if (no[3] % 2 != 0) listOdd.add("${no[3]}")
        if (no[4] % 2 != 0) listOdd.add("${no[4]}")
        if (no[5] % 2 != 0) listOdd.add("${no[5]}")
        return listOdd
    }

    private fun getEven(vararg no: Int): ArrayList<String> {
        val listEven = ArrayList<String>()
        if (no[0] % 2 == 0) listEven.add("${no[0]}")
        if (no[1] % 2 == 0) listEven.add("${no[1]}")
        if (no[2] % 2 == 0) listEven.add("${no[2]}")
        if (no[3] % 2 == 0) listEven.add("${no[3]}")
        if (no[4] % 2 == 0) listEven.add("${no[4]}")
        if (no[5] % 2 == 0) listEven.add("${no[5]}")
        return listEven
    }
}
