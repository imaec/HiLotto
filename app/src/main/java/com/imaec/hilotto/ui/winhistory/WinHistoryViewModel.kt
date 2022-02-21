package com.imaec.hilotto.ui.winhistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.FitNumberDTO
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.model.MyNumberDTO
import com.imaec.hilotto.ui.winhistory.WinHistoryActivity.Companion.LOTTO_LIST
import com.imaec.hilotto.ui.winhistory.WinHistoryActivity.Companion.MY_NUMBER
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WinHistoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _state = MutableLiveData<WinHistoryState>()
    val state: LiveData<WinHistoryState> get() = _state

    private val _lottoList = MutableLiveData<List<LottoDTO>>(savedStateHandle.get(LOTTO_LIST))
    val lottoList: LiveData<List<LottoDTO>> get() = _lottoList

    private val _myNumber = MutableLiveData<MyNumberDTO>(savedStateHandle.get(MY_NUMBER))
    val myNumber: LiveData<MyNumberDTO> get() = _myNumber

    private val _winList = MutableLiveData<List<MyNumberDTO>>(emptyList())
    val winList: LiveData<List<MyNumberDTO>> get() = _winList

    fun checkWin() {
        val listWinTemp = mutableListOf<MyNumberDTO>()
        lottoList.value?.map {
            val winNumbers = arrayOf(
                it.drwtNo1,
                it.drwtNo2,
                it.drwtNo3,
                it.drwtNo4,
                it.drwtNo5,
                it.drwtNo6
            )

            val fitNumberDTO = FitNumberDTO()
            val listTemp = arrayListOf<Int>()
            arrayOf(
                myNumber.value?.number1,
                myNumber.value?.number2,
                myNumber.value?.number3,
                myNumber.value?.number4,
                myNumber.value?.number5,
                myNumber.value?.number6
            ).forEach { myNumber ->
                winNumbers.forEach { winNumber ->
                    if (winNumber == myNumber) {
                        listTemp.add(myNumber)
                    }
                }
                if (it.bnusNo == myNumber) {
                    fitNumberDTO.numberBonus = myNumber
                }
            }

            fitNumberDTO.apply {
                listFitNumber.addAll(listTemp)
                listFitNumber.forEach { fitNumber ->
                    if (myNumber.value?.number1 == fitNumber) isFitNo1 = true
                    if (myNumber.value?.number2 == fitNumber) isFitNo2 = true
                    if (myNumber.value?.number3 == fitNumber) isFitNo3 = true
                    if (myNumber.value?.number4 == fitNumber) isFitNo4 = true
                    if (myNumber.value?.number5 == fitNumber) isFitNo5 = true
                    if (myNumber.value?.number6 == fitNumber) isFitNo6 = true
                }
                arrayOf(
                    myNumber.value?.number1,
                    myNumber.value?.number2,
                    myNumber.value?.number3,
                    myNumber.value?.number4,
                    myNumber.value?.number5,
                    myNumber.value?.number6
                ).forEach { number ->
                    listIsFitBonus.add(numberBonus == number)
                }
                rank = when (fitNumberDTO.listFitNumber.size) {
                    6 -> 1
                    5 -> if (fitNumberDTO.numberBonus > 0) 2 else 3
                    4 -> 4
                    3 -> 5
                    else -> 0
                }
                round = "${it.drwNo}회"
            }

            if (fitNumberDTO.listFitNumber.size >= 3) {
                listWinTemp.add(
                    myNumber.value!!.copy(
                        fitNumber = fitNumberDTO
                    )
                )
            }
        }
        _winList.value = listWinTemp
    }

    fun onClickSort() {
        _state.value = WinHistoryState.OnClickSort
    }

    fun sort(isAcs: Boolean = false, isWinSort: Boolean = false) {
        _winList.value =
            if (isWinSort) {
                winList.value!!.sortedByDescending {
                    it.fitNumber?.round?.split("회")?.get(0)?.toInt() ?: 0
                }.sortedBy { it.fitNumber?.rank }
            } else {
                if (isAcs) {
                    winList.value!!.sortedBy {
                        it.fitNumber?.round?.split("회")?.get(0)?.toInt() ?: 0
                    }
                } else {
                    winList.value!!.sortedByDescending {
                        it.fitNumber?.round?.split("회")?.get(0)?.toInt() ?: 0
                    }
                }
            }
    }
}
