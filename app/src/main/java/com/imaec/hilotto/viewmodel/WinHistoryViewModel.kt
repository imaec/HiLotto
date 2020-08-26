package com.imaec.hilotto.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.FitNumberDTO
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.room.entity.NumberEntity
import com.imaec.hilotto.ui.adapter.WinHistoryAdapter

class WinHistoryViewModel : BaseViewModel() {

    init {
        adapter = WinHistoryAdapter()
    }

    private val _listLotto = MutableLiveData<List<LottoDTO>>().set(ArrayList())
    val listLotto: LiveData<List<LottoDTO>> get() = _listLotto

    private val _number = MutableLiveData<NumberEntity>(NumberEntity())
    val number: LiveData<NumberEntity> get() = _number

    private val _listWin = MutableLiveData<List<NumberEntity>>(emptyList())
    val listWin: LiveData<List<NumberEntity>> get() = _listWin

    private val _listFitNumbers = MutableLiveData<List<FitNumberDTO>>().set(emptyList())
    val listFitNumbers: LiveData<List<FitNumberDTO>> get() = _listFitNumbers

    fun setListLotto(list: List<LottoDTO>) {
        _listLotto.value = list
    }

    fun setMyNumber(entity: NumberEntity) {
        _number.value = entity
    }

    fun checkWin() {
        val listWinTemp = arrayListOf<NumberEntity>()
        val listFitTemp = arrayListOf<FitNumberDTO>()
        _listLotto.value?.forEach { result ->
            val winNumbers = arrayOf(result.drwtNo1, result.drwtNo2, result.drwtNo3, result.drwtNo4, result.drwtNo5, result.drwtNo6)

            val fitNumberDTO = FitNumberDTO()
            val listTemp = arrayListOf<Int>()
            arrayOf(_number.value?.number1, _number.value?.number2, _number.value?.number3, _number.value?.number4, _number.value?.number5, _number.value?.number6).forEach { myNumber ->
                winNumbers.forEach {  winNumber ->
                    if (winNumber == myNumber) {
                        listTemp.add(myNumber)
                    }
                }
                if (result.bnusNo == myNumber) {
                    fitNumberDTO.numberBonus = myNumber
                }
            }

            fitNumberDTO.apply {
                listFitNumber.addAll(listTemp)
                rank = when (fitNumberDTO.listFitNumber.size) {
                    6 -> 1
                    5 -> if (fitNumberDTO.numberBonus > 0) 2 else 3
                    4 -> 4
                    3 -> 5
                    else -> 0
                }
                round = "${result.drwNo}회"
            }
            Log.d(TAG, "    ## ${listFitTemp.size + 1} fitNumber : $fitNumberDTO")

            if (fitNumberDTO.listFitNumber.size >= 3) {
                listWinTemp.add(_number.value!!)
                listFitTemp.add(fitNumberDTO)
            }
        }
        _listWin.value = listWinTemp
        _listFitNumbers.value = listFitTemp.sortedByDescending { it.round.split("회")[0].toInt() }

        Log.d(TAG, "    ## size : ${listFitTemp.size}")
    }

    fun sort(isAcs: Boolean = false) {
        _listFitNumbers.value =
            if (isAcs)
                _listFitNumbers.value!!.sortedBy { it.round.split("회")[0].toInt() }
            else
                _listFitNumbers.value!!.sortedByDescending { it.round.split("회")[0].toInt() }
    }
}