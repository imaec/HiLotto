package com.imaec.hilotto.repository

import android.util.Log
import com.google.firebase.database.*
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.utils.DateUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.HashMap

class FirebaseRepository {

    private val TAG = this::class.java.simpleName

    private var database = FirebaseDatabase.getInstance()
    private var ref = database.reference

    fun setWeek(curDrwNo: Int) {
        ref.child("week").setValue(curDrwNo)
    }

    fun getLottoList(callback: (List<LottoDTO>) -> Unit) {
        ref.child("number").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                ref.child("error").child(DateUtil.getDate("yyyy-MM-dd HH:mm:ss")).child("getLottoList()").setValue(databaseError.message)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val listCurrent = ArrayList<LottoDTO>()
                dataSnapshot.value?.let {
                    val listItem = (it as List<*>).reversed()
                    for (item in listItem) {
                        item?.let { map ->
                            val hashMap = map as HashMap<*, *>
                            val lotto = LottoDTO().apply {
                                drwNo = hashMap["no"].toString().toInt()
                                drwtNo1 = hashMap["no1"].toString().toInt()
                                drwtNo2 = hashMap["no2"].toString().toInt()
                                drwtNo3 = hashMap["no3"].toString().toInt()
                                drwtNo4 = hashMap["no4"].toString().toInt()
                                drwtNo5 = hashMap["no5"].toString().toInt()
                                drwtNo6 = hashMap["no6"].toString().toInt()
                                bnusNo = hashMap["bonusNo"].toString().toInt()
                                firstAccumamnt = hashMap["winnerTotal"].toString().toLong()
                                firstWinamnt = hashMap["winnerReward"].toString().toLong()
                                firstPrzwnerCo = hashMap["winnerCount"].toString().toInt()
                                drwNoDate = hashMap["date"].toString()
                            }
                            listCurrent.add(lotto)
                        }
                    }

                    callback(listCurrent)
                }
            }
        })
    }

    fun setLottoList(list: List<LottoDTO>) {
        for (lotto in list) {
            ref.child("number").child(lotto.drwNo.toString()).apply {
                child("date").setValue(lotto.drwNoDate)
                child("no").setValue(lotto.drwNo)
                child("no1").setValue(lotto.drwtNo1.toString())
                child("no2").setValue(lotto.drwtNo2.toString())
                child("no3").setValue(lotto.drwtNo3.toString())
                child("no4").setValue(lotto.drwtNo4.toString())
                child("no5").setValue(lotto.drwtNo5.toString())
                child("no6").setValue(lotto.drwtNo6.toString())
                child("bonusNo").setValue(lotto.bnusNo.toString())
                child("winnerTotal").setValue(lotto.firstAccumamnt.toString())
                child("winnerCount").setValue(lotto.firstPrzwnerCo.toString())
                child("winnerReward").setValue(lotto.firstWinamnt.toString())
            }
        }
    }
}