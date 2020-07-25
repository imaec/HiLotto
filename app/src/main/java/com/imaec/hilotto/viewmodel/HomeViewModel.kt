package com.imaec.hilotto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.ui.adapter.LatelyResultAdapter

class HomeViewModel : BaseViewModel() {

    init {
        adapter = LatelyResultAdapter()
    }

    private val _listLatelyResult = MutableLiveData<List<LottoDTO>>().set(ArrayList())
    val listLatelyResult: LiveData<List<LottoDTO>> get() = _listLatelyResult

    fun setListLatelyResult(listResult: List<LottoDTO>) {
        _listLatelyResult.value = listResult.subList(listResult.size-11, listResult.size-1).reversed()
    }
}