package com.imaec.hilotto.viewmodel

import com.google.firebase.firestore.FirebaseFirestore
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.utils.SharedPreferenceUtil

class HomeViewModel : BaseViewModel() {

    private val db = FirebaseFirestore.getInstance()

    var curDrwNo = 1

    private fun getLottoHtml(data: Int) {
        val parsingUrl = "https://www.dhlottery.co.kr/common.do?method=main&mainMode=default"
        val runnable = Runnable {
            getCurDrwNo(parsingUrl, data)
        }

        val thread = Thread(runnable)
        try {
            thread.start()
            thread.join()
        } catch (e: Exception) {
//            ref.child("error").child(Utils.getCurDate()).child("getLottoHtml()").setValue(e.localizedMessage)
//            getLotto()
        }
    }

    fun checkLotto(curDrwNo: Int) {
        this.curDrwNo = curDrwNo
        if (curDrwNo > 1) {
            getLottoHtml(curDrwNo)
            return
        }
    }
}