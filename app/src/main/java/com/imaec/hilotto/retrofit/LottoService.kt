package com.imaec.hilotto.retrofit

import com.imaec.hilotto.model.LottoDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface LottoService {

    @GET("common.do")
    fun callLottoNumber(
        @Query("drwNo") drwNo: Int,
        @Query("method") method: String = "getLottoNumber"
    ): Call<LottoDTO>
}
