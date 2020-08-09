package com.imaec.hilotto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.base.BaseViewModel

class RecommendViewModel : BaseViewModel() {

    private val _isVisible = MutableLiveData<Boolean>().set(false)
    val isVisible: LiveData<Boolean> get() = _isVisible

    private val _listIncludeNumber = MutableLiveData<ArrayList<String>>(arrayListOf("", "", "", "", "", ""))
    val listIncludeNumber: LiveData<ArrayList<String>> get() = _listIncludeNumber

    fun setVisible(visible: Boolean) {
        _isVisible.value = visible
    }

    fun setIncludeNumber(index: Int, number: String) {
        val listTemp = _listIncludeNumber.value!!
        listTemp[index] = number
        _listIncludeNumber.value = listTemp
    }

    fun removeNumber(index: Int) {
        val listTemp = _listIncludeNumber.value!!
        listTemp.removeAt(index)
        listTemp.add("")
        _listIncludeNumber.value = listTemp
    }
}