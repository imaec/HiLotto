package com.imaec.hilotto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.ui.adapter.LatelyResultAdapter
import javax.inject.Inject

class HomeViewModel @Inject constructor() : BaseViewModel() {

    init {
        adapter = LatelyResultAdapter()
    }

    private val _listLatelyResult = MutableLiveData<List<LottoDTO>>(ArrayList())
    val listLatelyResult: LiveData<List<LottoDTO>> get() = _listLatelyResult

    fun setListLatelyResult(listResult: List<LottoDTO>) {
        listResult.takeIf { it.isNotEmpty() }?.let {
            _listLatelyResult.value = listResult.subList(1, 10)
        }
    }

    fun setOnItemClickListener(onClick: (Any) -> Unit) {
        adapter.addOnClickListener { onClick(it) }
    }
}
