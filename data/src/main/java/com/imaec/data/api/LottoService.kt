package com.imaec.data.api

import com.imaec.data.entity.LottoEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface LottoService {

    @GET("common.do")
    fun callLottoNumber(
        @Query("drwNo") drwNo: Int,
        @Query("method") method: String = "getLottoNumber"
    ): LottoEntity
}
