package com.imaec.hilotto.ui.store

import com.imaec.hilotto.model.StoreVo

sealed class StoreState {

    object OnClickSearch : StoreState()

    data class OnClickStore(val store: StoreVo) : StoreState()

    data class SuccessCheck(val round: Int) : StoreState()

    data class FailCheck(val message: String) : StoreState()
}
