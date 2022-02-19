package com.imaec.hilotto.ui.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.StoreDTO
import com.imaec.hilotto.ui.store.StoreActivity.Companion.CUR_DRW_NO
import com.imaec.hilotto.ui.store.StoreActivity.Companion.STORE_LIST
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _state = MutableLiveData<StoreState>()
    val state: LiveData<StoreState> get() = _state

    val storeList = savedStateHandle.getLiveData<List<StoreDTO>>(STORE_LIST)

    private val _round = MutableLiveData("${savedStateHandle.get<Int>(CUR_DRW_NO) ?: 0}회")
    val round: LiveData<String> get() = _round

    fun onClickStore(store: StoreDTO) {
        _state.value = StoreState.OnClickStore(store)
    }
}
