package com.imaec.domain.repository

import com.imaec.domain.model.LottoDto

interface FirebaseRepository {

    fun setWeek(curDrwNo: Int)

    fun getLottoList(callback: (List<LottoDto>) -> Unit)

    fun setLottoList(list: List<LottoDto>)
}
