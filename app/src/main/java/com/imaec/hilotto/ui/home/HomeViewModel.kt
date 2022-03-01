package com.imaec.hilotto.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.LottoVo
import com.imaec.hilotto.model.StoreVo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {

    private val _state = MutableLiveData<HomeState>()
    val state: LiveData<HomeState> get() = _state

    private val _latelyResultList = MutableLiveData<List<LottoVo>>(ArrayList())
    val latelyResultList: LiveData<List<LottoVo>> get() = _latelyResultList

    fun setListLatelyResult(lottoList: List<LottoVo>) {
        lottoList.takeIf { it.isNotEmpty() }?.let {
            _latelyResultList.value = lottoList.subList(1, 10)
        }
    }

    fun onClickLately(lotto: LottoVo, lottoList: List<LottoVo>) {
        _state.value = HomeState.OnClickLately(lotto, lottoList)
    }

    fun onClickMore(lottoList: List<LottoVo>) {
        _state.value = HomeState.OnClickMore(lottoList)
    }

    fun onClickStore(curDrwNo: Int, storeList: List<StoreVo>, lottoList: List<LottoVo>) {
        _state.value = HomeState.OnClickStore(curDrwNo, storeList, lottoList)
    }
}
