package com.imaec.hilotto.ui.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.domain.successOr
import com.imaec.hilotto.domain.usecase.lotto.GetStoreUseCase
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.model.StoreDTO
import com.imaec.hilotto.ui.store.StoreActivity.Companion.CUR_DRW_NO
import com.imaec.hilotto.ui.store.StoreActivity.Companion.LOTTO_LIST
import com.imaec.hilotto.ui.store.StoreActivity.Companion.STORE_LIST
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getStoreUseCase: GetStoreUseCase
) : BaseViewModel() {

    private val _state = MutableLiveData<StoreState>()
    val state: LiveData<StoreState> get() = _state

    val lottoList = savedStateHandle.getLiveData<List<LottoDTO>>(LOTTO_LIST)

    private val _storeList = MutableLiveData<List<StoreDTO>>(savedStateHandle.get(STORE_LIST))
    val storeList: LiveData<List<StoreDTO>> get() = _storeList

    private val _round = MutableLiveData("<${savedStateHandle.get<Int>(CUR_DRW_NO) ?: 0}회>")
    val round: LiveData<String> get() = _round

    fun onClickSearch() {
        _state.value = StoreState.OnClickSearch
    }

    fun onClickStore(store: StoreDTO) {
        _state.value = StoreState.OnClickStore(store)
    }

    fun checkSearchRound(keyword: String) {
        if (keyword.isEmpty()) {
            _state.value = StoreState.FailCheck("검색할 회차를 입력해주세요.")
        } else if (keyword.toInt() < 1 || keyword.toInt() > lottoList.value!![0].drwNo) {
            _state.value = StoreState.FailCheck(
                "검색할 회차를 1 ~ ${lottoList.value!![0].drwNo} 사이로 입력해주세요."
            )
        } else {
            _state.value = StoreState.SuccessCheck(keyword.toInt())
        }
    }

    fun getStore(drwNo: Int) {
        viewModelScope.launch {
            getStoreUseCase(drwNo).successOr(null)?.let {
                _round.value = "<${drwNo}회>"
                _storeList.value = it
            }
        }
    }
}
