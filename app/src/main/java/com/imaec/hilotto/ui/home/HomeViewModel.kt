package com.imaec.hilotto.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.model.StoreDTO
import javax.inject.Inject

class HomeViewModel @Inject constructor() : BaseViewModel() {

    private val _state = MutableLiveData<HomeState>()
    val state: LiveData<HomeState> get() = _state

    private val _latelyResultList = MutableLiveData<List<LottoDTO>>(ArrayList())
    val latelyResultList: LiveData<List<LottoDTO>> get() = _latelyResultList

    fun setListLatelyResult(lottoList: List<LottoDTO>) {
        lottoList.takeIf { it.isNotEmpty() }?.let {
            _latelyResultList.value = lottoList.subList(1, 10)
        }
    }

    fun onClickLately(lotto: LottoDTO, lottoList: List<LottoDTO>) {
        _state.value = HomeState.OnClickLately(lotto, lottoList)
    }

    fun onClickMore(lottoList: List<LottoDTO>) {
        _state.value = HomeState.OnClickMore(lottoList)
    }

    fun onClickStore(curDrwNo: Int, storeList: List<StoreDTO>, lottoList: List<LottoDTO>) {
        _state.value = HomeState.OnClickStore(curDrwNo, storeList, lottoList)
    }
}
