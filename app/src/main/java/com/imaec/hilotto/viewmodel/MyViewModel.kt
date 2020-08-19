package com.imaec.hilotto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.repository.NumberRepository
import com.imaec.hilotto.room.entity.NumberEntity
import com.imaec.hilotto.ui.adapter.MyNumberAdapter
import kotlinx.coroutines.launch

class MyViewModel(
    private val repository: NumberRepository
) : BaseViewModel() {

    init {
        adapter = MyNumberAdapter()
    }

    private val _listNumber = MutableLiveData<List<NumberEntity>>().set(emptyList())
    val listNumber: LiveData<List<NumberEntity>> get() = _listNumber

    fun getNumbers() {
        viewModelScope.launch {
            _listNumber.value = repository.selectAll()
        }
    }
}