package com.imaec.hilotto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.domain.successOr
import com.imaec.hilotto.domain.usecase.number.DeleteUseCase
import com.imaec.hilotto.domain.usecase.number.InsertAllUseCase
import com.imaec.hilotto.domain.usecase.number.SelectAllUseCase
import com.imaec.hilotto.domain.usecase.number.SelectByNumbersUseCase
import com.imaec.hilotto.model.FitNumberDTO
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.room.entity.NumberEntity
import com.imaec.hilotto.ui.adapter.MyNumberAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val selectAllUseCase: SelectAllUseCase,
    private val selectByNumbersUseCase: SelectByNumbersUseCase,
    private val insertAllUseCase: InsertAllUseCase,
    private val deleteUseCase: DeleteUseCase,
) : BaseViewModel() {

    init {
        adapter = MyNumberAdapter().apply {
            setHasStableIds(true)
        }
    }

    private val _listNumber = MutableLiveData<List<NumberEntity>>(emptyList())
    val listNumber: LiveData<List<NumberEntity>> get() = _listNumber

    private val _listFitNumbers = MutableLiveData<List<FitNumberDTO>>(emptyList())
    val listFitNumbers: LiveData<List<FitNumberDTO>> get() = _listFitNumbers

    fun getNumbers() {
        viewModelScope.launch {
            _listNumber.value = selectAllUseCase().successOr(emptyList())
        }
    }

    fun deleteNumber(entity: NumberEntity) {
        viewModelScope.launch {
            deleteUseCase(entity)
            _listNumber.value = selectAllUseCase().successOr(emptyList())
        }
    }

    fun checkWin(result: LottoDTO) {
        val winNumbers = arrayOf(
            result.drwtNo1,
            result.drwtNo2,
            result.drwtNo3,
            result.drwtNo4,
            result.drwtNo5,
            result.drwtNo6
        )
        val listFitTemp = arrayListOf<FitNumberDTO>()
        _listNumber.value!!.forEach {
            val fitNumberDTO = FitNumberDTO()
            val listTemp = arrayListOf<Int>()
            arrayOf(
                it.number1,
                it.number2,
                it.number3,
                it.number4,
                it.number5,
                it.number6
            ).forEach { myNumber ->
                winNumbers.forEach { winNumber ->
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
            }
            listFitTemp.add(fitNumberDTO)
        }
        _listFitNumbers.value = listFitTemp
    }

    fun setOnNumberClickListener(onClick: (Any) -> Unit) {
        adapter.addOnClickListener { onClick(it) }
    }

    fun setOnNumberLongClickListener(onClick: (Any) -> Unit) {
        adapter.addOnLongClickListener { onClick(it) }
    }

    fun saveNumbers(list: List<NumberEntity>, callback: () -> Unit) {
        val listTemp = ArrayList<NumberEntity>().apply {
            addAll(list)
        }
        viewModelScope.launch {
            list.forEach { entity ->
                if (selectByNumbersUseCase(entity).successOr(0) > 0) {
                    // ALREADY EXIST
                    listTemp.remove(entity)
                }
            }
            if (listTemp.size > 0) insertAllUseCase(listTemp)
            callback()
        }
    }
}
