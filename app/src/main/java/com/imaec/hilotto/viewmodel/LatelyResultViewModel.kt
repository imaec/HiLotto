package com.imaec.hilotto.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.ui.adapter.LatelyResultPageAdapter

class LatelyResultViewModel : BaseViewModel() {

    init {
        adapter = LatelyResultPageAdapter()
    }

    private val _listLatelyResult = MutableLiveData<List<LottoDTO>>().set(ArrayList())
    val listLatelyResult: LiveData<List<LottoDTO>> get() = _listLatelyResult

    fun setListLatelyResult(listResult: List<LottoDTO>) {
        _listLatelyResult.value = listResult
    }

    fun checkSearchRound(keyword: String): String {
        Log.d(TAG, "    ## round : ${listLatelyResult.value!![0].drwNo}")
        return if (keyword.isEmpty()) {
            "검색할 회차를 입력해주세요."
        } else if (keyword.toInt() < 1 || keyword.toInt() > listLatelyResult.value!![0].drwNo) {
            "검색할 회차를 1 ~ ${listLatelyResult.value!![0].drwNo} 사이로 입력해주세요."
        } else {
            "OK"
        }
    }
}