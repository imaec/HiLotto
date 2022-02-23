package com.imaec.hilotto.data.repository

import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.model.StoreDTO

interface LottoRepository {

    suspend fun getCurDrwNo(): Int

    fun getData(drwNo: Int, onResponse: (LottoDTO) -> Unit, onFailure: () -> Unit)

    suspend fun getStore(drwNo: Int): List<StoreDTO>
}
