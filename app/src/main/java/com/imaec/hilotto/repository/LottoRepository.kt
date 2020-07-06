package com.imaec.hilotto.repository

import android.util.Log
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.retrofit.LottoService
import com.imaec.hilotto.retrofit.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LottoRepository {

    suspend fun getCurDrwNo(strUrl: String, callback: (Int) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                val doc = Jsoup.connect(strUrl).get()
                val element = doc.getElementById("lottoDrwNo")
                val curDrwNo = element.text().toInt()
                callback(curDrwNo)
            } catch (e: Exception) {
                callback(-1)
            }
        }
    }

    fun getData(drwNo: Int, onResponse: (LottoDTO) -> Unit, onFailure: () -> Unit) {
        val lottoService = RetrofitClient.getInstance().create(LottoService::class.java)
        val callLottoNumber = lottoService.callLottoNumber(drwNo)
        callLottoNumber.clone().enqueue(object : Callback<LottoDTO> {
            override fun onResponse(call: Call<LottoDTO>, response: Response<LottoDTO>) {
                response.body()?.let {
                    Log.d("response", " ## : $it")
                    onResponse(it)
                } ?: run {
                    onFailure()
                }
            }

            override fun onFailure(call: Call<LottoDTO>, t: Throwable) {
                Log.d("fail", " ## : ${t.message}")
                onFailure()
            }
        })
    }
}