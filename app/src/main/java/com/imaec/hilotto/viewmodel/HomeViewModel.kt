package com.imaec.hilotto.viewmodel

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.repository.LottoRepository
import com.imaec.hilotto.utils.SharedPreferenceUtil
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: LottoRepository
) : BaseViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private var curDrwNo = 1

    private fun getLottoHtml(curDrwNo: Int) {


//        val runnable = Runnable {
//            getCurDrwNo(parsingUrl, curDrwNo)
//        }
//
//        val thread = Thread(runnable)
//        try {
//            thread.start()
//            thread.join()
//        } catch (e: Exception) {
////            ref.child("error").child(Utils.getCurDate()).child("getLottoHtml()").setValue(e.localizedMessage)
////            getLotto()
//        }
    }

    fun checkLotto() {
        val parsingUrl = "https://www.dhlottery.co.kr/common.do?method=main&mainMode=default"
        viewModelScope.launch {
            repository.getCurDrwNo(parsingUrl) {
                curDrwNo = it
                Log.d(TAG, "    ## curDrwNo : $it")
            }
        }
    }
}