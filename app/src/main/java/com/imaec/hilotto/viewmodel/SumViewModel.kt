package com.imaec.hilotto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.model.SumDTO
import com.imaec.hilotto.ui.adapter.SumAdapter

class SumViewModel : BaseViewModel() {

    init {
        adapter = SumAdapter()
    }

    private val _statisticsNo = MutableLiveData<Int>(20)
    val statisticsNo: LiveData<Int> get() = _statisticsNo

    private val _listSum = MutableLiveData<List<SumDTO>>(ArrayList())
    val listSum: LiveData<List<SumDTO>> get() = _listSum

    private val _sumAvg = MutableLiveData<String>("")
    val sumAvg: LiveData<String> get() = _sumAvg

    private val _sumMin = MutableLiveData<Int>(21)
    val sumMin: LiveData<Int> get() = _sumMin

    private val _sumMax = MutableLiveData<Int>(255)
    val sumMax: LiveData<Int> get() = _sumMax

    private fun setSum() {
        _listSum.value?.let {
            var sum = 0
            it.forEach { dto ->
                sum += dto.sum
            }
            _sumAvg.value = "${it.size}회 합계 평균 : ${sum / it.size}"
        }
    }

    fun setStatisticsNo(no: Int) {
        _statisticsNo.value = no
    }

    fun setListSum(listResult: List<LottoDTO>, isInclude: Boolean = false) {
        val listTemp = ArrayList<SumDTO>()
        listResult.subList(0, statisticsNo.value ?: 20).forEach {
            if (isInclude) listTemp.add(SumDTO("${it.drwNo}회", it.drwtNo1 + it.drwtNo2 + it.drwtNo3 + it.drwtNo4 + it.drwtNo5 + it.drwtNo6 + it.bnusNo))
            else listTemp.add(SumDTO("${it.drwNo}회", it.drwtNo1 + it.drwtNo2 + it.drwtNo3 + it.drwtNo4 + it.drwtNo5 + it.drwtNo6))
        }

        _sumMin.value = if (isInclude) 28 else 21
        _sumMax.value = if (isInclude) 294 else 255
        (adapter as SumAdapter).setSumMax(sumMax.value!!)

        _listSum.value = listTemp
        setSum()
    }
}
