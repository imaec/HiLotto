package com.imaec.hilotto.repository

import com.imaec.hilotto.utils.SharedPreferenceUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class LottoRepository {

    suspend fun getCurDrwNo(strUrl: String, callback: (Int) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                val doc = Jsoup.connect(strUrl).get()
                val element = doc.getElementById("lottoDrwNo")
                val curDrwNo = element.text().toInt()
                callback(curDrwNo)
//                // 최신 데이터, Firebase에서 데이터 가져옴
//                if (curDrwNo == data) {
//                    // SharedPreferenceUtil.putValue(this, SharedPreferenceUtil.KEY.PREF_WEEK, curDrwNo)
//                    getLotto()
//                    return
//                }
//
//                // 최신 데이터가 적용되지 않음, 로또 사이트에서 번호 긁어옴
//                if (curDrwNo > data) {
//                    val gap = curDrwNo - data
//                    for (drwNo in (data + 1)..curDrwNo) {
//                        this.drwNo = drwNo
//                        getData(gap)
//                    }
//                }
            } catch (e: Exception) {
                callback(-1)
            }
        }
    }
}