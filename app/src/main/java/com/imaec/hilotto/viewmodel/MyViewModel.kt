package com.imaec.hilotto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.FitNumberDTO
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.repository.NumberRepository
import com.imaec.hilotto.room.entity.NumberEntity
import com.imaec.hilotto.ui.adapter.MyNumberAdapter
import kotlinx.coroutines.launch

class MyViewModel(
    private val repository: NumberRepository
) : BaseViewModel() {

    init {
        adapter = MyNumberAdapter().apply {
            setHasStableIds(true)
        }
    }

    private val _listNumber = MutableLiveData<List<NumberEntity>>().set(emptyList())
    val listNumber: LiveData<List<NumberEntity>> get() = _listNumber

    private val _listFitNumbers = MutableLiveData<List<FitNumberDTO>>().set(emptyList())
    val listFitNumbers: LiveData<List<FitNumberDTO>> get() = _listFitNumbers

    fun getNumbers() {
        viewModelScope.launch {
            _listNumber.value = repository.selectAll()
        }
    }

    fun deleteNumber(entity: NumberEntity) {
        viewModelScope.launch {
            repository.delete(entity)
            _listNumber.value = repository.selectAll()
        }
    }

    fun checkWin(result: LottoDTO) {
        val winNumbers = arrayOf(result.drwtNo1, result.drwtNo2, result.drwtNo3, result.drwtNo4, result.drwtNo5, result.drwtNo6)
        val listFitTemp = arrayListOf<FitNumberDTO>()
        _listNumber.value!!.forEach {
            val fitNumberDTO = FitNumberDTO()
            val listTemp = arrayListOf<Int>()
            arrayOf(it.number1, it.number2, it.number3, it.number4, it.number5, it.number6).forEach { myNumber ->
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

    fun saveNumbers(list: List<NumberEntity>, callback: (Boolean) -> Unit) {
        val listTemp = ArrayList<NumberEntity>().apply {
            addAll(list)
        }
        viewModelScope.launch {
            list.forEach { entity ->
                if (repository.selectByNumbers(entity) > 0) {
                    // ALREADY EXIST
                    listTemp.remove(entity)
                }
            }
            if (listTemp.size > 0) repository.insertAll(listTemp)
            callback(true)
        }
    }
}