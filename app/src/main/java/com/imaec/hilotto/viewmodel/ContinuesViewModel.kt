package com.imaec.hilotto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.Lotto
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.ContinueDTO
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.ui.adapter.ContinuesAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContinuesViewModel @Inject constructor() : BaseViewModel() {

    init {
        adapter = ContinuesAdapter()
    }

    private val _statisticsNo = MutableLiveData(20)
    val statisticsNo: LiveData<Int> get() = _statisticsNo

    private val _listContinues = MutableLiveData<List<ContinueDTO>>(ArrayList())
    val listContinues: LiveData<List<ContinueDTO>> get() = _listContinues

    fun setStatisticsNo(no: Int) {
        _statisticsNo.value = no
    }

    fun setPickedNum(listResult: List<LottoDTO>) {
        val listTemp = ArrayList<ContinueDTO>()
        listResult.subList(0, statisticsNo.value ?: 20).forEach {
            val continues = Lotto.getContinues(
                it.drwtNo1,
                it.drwtNo2,
                it.drwtNo3,
                it.drwtNo4,
                it.drwtNo5,
                it.drwtNo6
            )
            val continueString = Lotto.getSequenceString(continues)
            val continueNum = Lotto.getSequenceNumber(
                it.drwtNo1,
                it.drwtNo2,
                it.drwtNo3,
                it.drwtNo4,
                it.drwtNo5,
                it.drwtNo6
            )

            val dto = ContinueDTO(
                "${it.drwNo}회",
                arrayListOf(
                    "${it.drwtNo1}",
                    "${it.drwtNo2}",
                    "${it.drwtNo3}",
                    "${it.drwtNo4}",
                    "${it.drwtNo5}",
                    "${it.drwtNo6}"
                ),
                continueString,
                continueNum
            )

            listTemp.add(dto)
        }
        _listContinues.value = listTemp
    }
}
