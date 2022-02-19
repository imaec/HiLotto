package com.imaec.hilotto.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var instance: Retrofit? = null

    fun getInstance(): Retrofit {
        if (instance == null) {
            val SERVICE_URL = "https://www.dhlottery.co.kr/"
            instance = Retrofit.Builder()
                .baseUrl(SERVICE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return instance!!
    }
}
