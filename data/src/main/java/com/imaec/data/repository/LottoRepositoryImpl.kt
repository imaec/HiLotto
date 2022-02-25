package com.imaec.data.repository

import com.imaec.data.api.LottoService
import com.imaec.domain.model.StoreDto
import com.imaec.domain.repository.LottoRepository
import org.jsoup.Jsoup
import timber.log.Timber

class LottoRepositoryImpl(private val service: LottoService) : LottoRepository {

    override suspend fun getCurDrwNo(): Int {
        return try {
            val doc = Jsoup.connect(
                "https://www.dhlottery.co.kr/common.do?method=main&mainMode=default"
            ).timeout(10000).get()
            val element = doc.getElementById("lottoDrwNo")
            val curDrwNo = element.text().toInt()
            curDrwNo
        } catch (e: Exception) {
            -1
        }
    }

    override fun getData(drwNo: Int) = service.callLottoNumber(drwNo).toDto()

    override suspend fun getStore(drwNo: Int): List<StoreDto> {
        val listStore = mutableListOf<StoreDto>()
        try {
            val doc = Jsoup
                .connect("https://www.dhlottery.co.kr/store.do?method=topStore&pageGubun=L645")
                .timeout(10000)
                .data("gameNo", "5133")
                .data("drwNo", "$drwNo")
                .post()
            val elements = doc.getElementsByClass("group_content")
            val tbody = elements[0].getElementsByTag("tbody")[0]
            val arrTr = tbody.getElementsByTag("tr")
            arrTr.forEach {
                listStore.add(
                    StoreDto(
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
