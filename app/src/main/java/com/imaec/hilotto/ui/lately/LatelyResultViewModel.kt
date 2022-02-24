package com.imaec.hilotto.ui.lately

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.LatelyResultVo
import com.imaec.hilotto.model.LottoVo
import com.imaec.hilotto.ui.lately.LatelyResultActivity.Companion.LOTTO_LIST
import com.imaec.hilotto.ui.lately.LatelyResultActivity.Companion.POSITION
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LatelyResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _state = MutableLiveData<LatelyState>()
    val state: LiveData<LatelyState> get() = _state

    private val _lottoList = MutableLiveData<List<LottoVo>>(
        savedStateHandle.get(LOTTO_LIST)
    )
    val lottoList: LiveData<List<LottoVo>> get() = _lottoList

    private val _latelyResultList = MediatorLiveData<List<LatelyResultVo>>().apply {
        addSource(lottoList) { list ->
            value = list.map { it.toLatelyResultVo() }
        }
    }
    val latelyResultList: LiveData<List<LatelyResultVo>> get() = _latelyResultList

    val position = savedStateHandle.get<Int>(POSITION) ?: 0

    fun onClickSearch() {
        _state.value = LatelyState.OnClickSearch
    }

    fun checkSearchRound(keyword: String) {
        if (keyword.isEmpty()) {
            _state.value = LatelyState.FailCheck("검색할 회차를 입력해주세요.")
        } else if (keyword.toInt() < 1 || keyword.toInt() > lottoList.value!![0].drwNo) {
            _state.value = LatelyState.FailCheck(
                "검색할 회차를 1 ~ ${lottoList.value!![0].drwNo} 사이로 입력해주세요."
            )
        } else {
            _state.value = LatelyState.SuccessCheck(getCurrentRound() - keyword.toInt())
        }
    }

    private fun getCurrentRound(): Int = lottoList.value?.let {
        it[0].drwNo
    } ?: run {
        0
    }
}
