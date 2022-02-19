package com.imaec.hilotto.data.repository

import com.imaec.hilotto.model.LottoDTO

interface FirebaseRepository {

    fun setWeek(curDrwNo: Int)

    fun getLottoList(callback: (List<LottoDTO>) -> Unit)

    fun setLottoList(list: List<LottoDTO>)
}
