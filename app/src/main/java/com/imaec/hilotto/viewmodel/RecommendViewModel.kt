package com.imaec.hilotto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.domain.successOr
import com.imaec.hilotto.domain.usecase.number.InsertUseCase
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.domain.usecase.number.SelectByNumbersUseCase
import com.imaec.hilotto.room.entity.NumberEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendViewModel @Inject constructor(
    private val selectByNumbersUseCase: SelectByNumbersUseCase,
    private val insertUseCase: InsertUseCase
) : BaseViewModel() {

    private val _isVisible = MutableLiveData(false)
    val isVisible: LiveData<Boolean> get() = _isVisible

    private val _listIncludeNumber = MutableLiveData(arrayListOf("", "", "", "", "", ""))
    val listIncludeNumber: LiveData<ArrayList<String>> get() = _listIncludeNumber

    private val _listNotIncludeNumber = MutableLiveData<ArrayList<String>>(arrayListOf())
    val listNotIncludeNumber: LiveData<ArrayList<String>> get() = _listNotIncludeNumber

    private val _listResult = MutableLiveData<List<LottoDTO>>(ArrayList())
    val listResult: LiveData<List<LottoDTO>> get() = _listResult

    fun setVisible(visible: Boolean) {
        _isVisible.value = visible
    }

    fun checkIncludeNumber(number: String): Boolean {
        _listIncludeNumber.value?.let {
            it.forEach { includeNumber ->
                if (includeNumber == number) return false
            }
        }
        return true
    }

    fun checkNotIncludeNumber(number: String): Boolean {
        _listNotIncludeNumber.value?.let {
            it.forEach { notIncludeNumber ->
                if (notIncludeNumber == number) return false
            }
        }
        return true
    }

    fun setIncludeNumber(index: Int, number: String) {
        val listTemp = _listIncludeNumber.value!!
        listTemp[index] = number
        _listIncludeNumber.value = listTemp
    }

    fun removeIncludeNumber(index: Int) {
        val listTemp = _listIncludeNumber.value!!
        listTemp.removeAt(index)
        listTemp.add("")
        _listIncludeNumber.value = listTemp
    }

    fun addNotIncludeNumber(number: String) {
        val listTemp = _listNotIncludeNumber.value!!
        listTemp.forEach {
            if (it == number) return
        }
        listTemp.add(number)
        _listNotIncludeNumber.value = listTemp
    }

    fun removeNotIncludeNumber(number: String) {
        val listTemp = _listNotIncludeNumber.value!!
        if (listTemp.contains(number)) {
            listTemp.remove(number)
        }
        _listNotIncludeNumber.value = listTemp
    }

    fun setListResult(list: List<LottoDTO>) {
        _listResult.value = list
    }

    fun saveNumber(entity: NumberEntity, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            if (selectByNumbersUseCase(entity).successOr(0) > 0) {
                // ALREADY EXIST
                callback(false)
                return@launch
            }
            insertUseCase(entity)
            callback(true)
            _listIncludeNumber.value = arrayListOf("", "", "", "", "", "")
        }
    }
}
