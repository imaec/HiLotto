package com.imaec.domain.repository

import com.imaec.domain.model.LottoDto
import com.imaec.domain.model.StoreDto

interface LottoRepository {

    suspend fun getCurDrwNo(): Int

    fun getData(drwNo: Int, onResponse: (LottoDto) -> Unit, onFailure: () -> Unit)

    suspend fun getStore(drwNo: Int): List<StoreDto>
}
