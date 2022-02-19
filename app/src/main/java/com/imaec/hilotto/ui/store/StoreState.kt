package com.imaec.hilotto.ui.store

import com.imaec.hilotto.model.StoreDTO

sealed class StoreState {

    data class OnClickStore(val store: StoreDTO) : StoreState()
}
