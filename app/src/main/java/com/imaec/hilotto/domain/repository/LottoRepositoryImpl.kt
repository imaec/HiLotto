package com.imaec.hilotto.domain.repository

import com.imaec.hilotto.URL_LOTTO
import com.imaec.hilotto.URL_STORE
import com.imaec.hilotto.data.repository.LottoRepository
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.model.StoreDTO
import com.imaec.hilotto.retrofit.LottoService
import com.imaec.hilotto.retrofit.RetrofitClient
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class LottoRepositoryImpl : LottoRepository {

    override suspend fun getCurDrwNo(): Int {
        return try {
            val doc = Jsoup.connect(URL_LOTTO).timeout(10000).get()
            val element = doc.getElementById("lottoDrwNo")
            val curDrwNo = element.text().toInt()
            curDrwNo
        } catch (e: Exception) {
            -1
        }
    }

    override fun getData(drwNo: Int, onResponse: (LottoDTO) -> Unit, onFailure: () -> Unit) {
        val lottoService = RetrofitClient.getInstance().create(LottoService::class.java)
        val callLottoNumber = lottoService.callLottoNumber(drwNo)
        callLottoNumber.clone().enqueue(object : Callback<LottoDTO> {
            override fun onResponse(call: Call<LottoDTO>, response: Response<LottoDTO>) {
                response.body()?.let {
                    onResponse(it)
                } ?: run {
                    onFailure()
                }
            }

            override fun onFailure(call: Call<LottoDTO>, t: Throwable) {
                onFailure()
            }
        })
    }

    override suspend fun getStore(drwNo: Int): List<StoreDTO> {
        val listStore = mutableListOf<StoreDTO>()
        try {
            val doc = Jsoup.connect(URL_STORE)
                .timeout(10000)
                .data("gameNo", "5133")
                .data("drwNo", "$drwNo")
                .post()
            val elements = doc.getElementsByClass("group_content")
            val tbody = elements[0].getElementsByTag("tbody")[0]
            val arrTr = tbody.getElementsByTag("tr")
            arrTr.forEach {
                listStore.add(
                    StoreDTO(
                        it.getElementsByTag("td")[1].text(),
                        it.getElementsByTag("td")[2].text(),
                        it.getElementsByTag("td")[3].text()
                    )
                )
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
        return listStore
    }
}
