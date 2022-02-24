package com.imaec.hilotto.ui.home

import com.imaec.hilotto.model.LottoVo
import com.imaec.hilotto.model.StoreVo

sealed class HomeState {

    data class OnClickLately(val lotto: LottoVo, val lottoList: List<LottoVo>) : HomeState()

    data class OnClickMore(val lottoList: List<LottoVo>) : HomeState()

    data class OnClickStore(
        val curDrwNo: Int,
        val storeList: List<StoreVo>,
        val lottoList: List<LottoVo>
    ) : HomeState()
}
