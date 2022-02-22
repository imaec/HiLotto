package com.imaec.hilotto.data.repository

import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.model.StoreDTO

interface LottoRepository {

    suspend fun getCurDrwNo(strUrl: String, callback: (Int) -> Unit)

    fun getData(drwNo: Int, onResponse: (LottoDTO) -> Unit, onFailure: () -> Unit)

    suspend fun getStore(drwNo: Int, callback: (List<StoreDTO>) -> Unit)
}
