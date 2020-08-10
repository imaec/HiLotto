package com.imaec.hilotto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.base.BaseViewModel

class RecommendViewModel : BaseViewModel() {

    private val _isVisible = MutableLiveData<Boolean>().set(false)
    val isVisible: LiveData<Boolean> get() = _isVisible

    private val _listIncludeNumber = MutableLiveData<ArrayList<String>>(arrayListOf("", "", "", "", "", ""))
    val listIncludeNumber: LiveData<ArrayList<String>> get() = _listIncludeNumber

    private val _listNotIncludeNumber = MutableLiveData<ArrayList<String>>(arrayListOf())
    val listNotIncludeNumber: LiveData<ArrayList<String>> get() = _listNotIncludeNumber

    fun setVisible(visible: Boolean) {
        _isVisible.value = visible
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
}