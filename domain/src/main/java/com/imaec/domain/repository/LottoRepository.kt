package com.imaec.domain.repository

import com.imaec.domain.model.LottoDto
import com.imaec.domain.model.StoreDto

interface LottoRepository {

    suspend fun getCurDrwNo(): Int

    suspend fun getLottoNumber(drwNo: Int): LottoDto

    suspend fun getStore(drwNo: Int): List<StoreDto>
}
