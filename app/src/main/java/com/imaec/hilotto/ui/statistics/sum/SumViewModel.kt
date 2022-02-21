package com.imaec.hilotto.ui.statistics.sum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.model.SumDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SumViewModel @Inject constructor() : BaseViewModel() {

    private val _statisticsNo = MutableLiveData(20)
    val statisticsNo: LiveData<Int> get() = _statisticsNo

    private val _sumList = MutableLiveData<List<SumDTO>>()
    val sumList: LiveData<List<SumDTO>> get() = _sumList

    private val _sumAvg = MutableLiveData("")
    val sumAvg: LiveData<String> get() = _sumAvg

    private val _sumMin = MutableLiveData(21)
    val sumMin: LiveData<Int> get() = _sumMin

    private val _sumMax = MutableLiveData(255)
    val sumMax: LiveData<Int> get() = _sumMax

    fun setStatisticsNo(no: Int) {
        _statisticsNo.value = no
    }

    fun setSumList(lottoList: List<LottoDTO>?, isInclude: Boolean = false) {
        lottoList ?: return

        val listTemp = mutableListOf<SumDTO>()
        lottoList.subList(0, statisticsNo.value ?: 20).forEach {
            listTemp.add(
                SumDTO(
                    round = "${it.drwNo}회",
                    sum = it.drwtNo1 +
                        it.drwtNo2 +
                        it.drwtNo3 +
                        it.drwtNo4 +
                        it.drwtNo5 +
                        it.drwtNo6 +
                        if (isInclude) it.bnusNo else 0
                )
            )
        }

        _sumMin.value = if (isInclude) 28 else 21
        _sumMax.value = if (isInclude) 294 else 255
        _sumList.value = listTemp
        setSum(listTemp)
    }

    private fun setSum(list: List<SumDTO>) {
        var sum = 0
        list.forEach { dto ->
            sum += dto.sum
            _sumAvg.value = "${list.size}회 합계 평균 : ${sum / list.size}"
        }
    }
}
