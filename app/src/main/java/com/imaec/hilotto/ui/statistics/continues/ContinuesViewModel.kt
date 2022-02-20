package com.imaec.hilotto.ui.statistics.continues

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.Lotto
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.ContinueDTO
import com.imaec.hilotto.model.LottoDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContinuesViewModel @Inject constructor() : BaseViewModel() {

    private val _statisticsNo = MutableLiveData(20)
    val statisticsNo: LiveData<Int> get() = _statisticsNo

    private val _continuesList = MutableLiveData<List<ContinueDTO>>(ArrayList())
    val continuesList: LiveData<List<ContinueDTO>> get() = _continuesList

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
                no = "${it.drwNo}회",
                list = arrayListOf(
                    "${it.drwtNo1}",
                    "${it.drwtNo2}",
                    "${it.drwtNo3}",
                    "${it.drwtNo4}",
                    "${it.drwtNo5}",
                    "${it.drwtNo6}"
                ),
                content = continueString,
                continueNum = continueNum
            )

            listTemp.add(dto)
        }
        _continuesList.value = listTemp
    }
}
