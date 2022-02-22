package com.imaec.hilotto.domain.repository

import com.imaec.hilotto.URL_STORE
import com.imaec.hilotto.data.repository.LottoRepository
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.model.StoreDTO
import com.imaec.hilotto.retrofit.LottoService
import com.imaec.hilotto.retrofit.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LottoRepositoryImpl : LottoRepository {

    override suspend fun getCurDrwNo(strUrl: String, callback: (Int) -> Unit) {
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

    override suspend fun getStore(drwNo: Int, callback: (List<StoreDTO>) -> Unit) {
        withContext(Dispatchers.IO) {
            val listStore = ArrayList<StoreDTO>()
            try {
                val doc = Jsoup.connect(URL_STORE)
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
            }
            callback(listStore)
        }
    }
}
