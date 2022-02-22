package com.imaec.hilotto.ui.home

import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.model.StoreDTO

sealed class HomeState {

    data class OnClickLately(val lotto: LottoDTO, val lottoList: List<LottoDTO>) : HomeState()

    data class OnClickMore(val lottoList: List<LottoDTO>) : HomeState()

    data class OnClickStore(
        val curDrwNo: Int,
        val storeList: List<StoreDTO>,
        val lottoList: List<LottoDTO>
    ) : HomeState()
}
