package com.imaec.data.repository

import android.annotation.SuppressLint
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.imaec.domain.model.LottoDto
import com.imaec.domain.repository.FirebaseRepository
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.collections.ArrayList

class FirebaseRepositoryImpl(
    private val ref: DatabaseReference
) : FirebaseRepository {

    override fun setWeek(curDrwNo: Int) {
        ref.child("week").setValue(curDrwNo)
    }

    @SuppressLint("SimpleDateFormat")
    override fun getLottoList(callback: (List<LottoDto>) -> Unit) {
        ref.child("number").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                ref.child("error")
                    .child(SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date()))
                    .child("getLottoList()")
                    .setValue(databaseError.message)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val listCurrent = ArrayList<LottoDto>()
                dataSnapshot.value?.let {
                    val listItem = (it as List<*>).reversed()
                    for (item in listItem) {
                        item?.let { map ->
                            val hashMap = map as HashMap<*, *>
                            val lotto = LottoDto(
                                drwNo = hashMap["no"].toString().toInt(),
                                drwtNo1 = hashMap["no1"].toString().toInt(),
                                drwtNo2 = hashMap["no2"].toString().toInt(),
                                drwtNo3 = hashMap["no3"].toString().toInt(),
                                drwtNo4 = hashMap["no4"].toString().toInt(),
                                drwtNo5 = hashMap["no5"].toString().toInt(),
                                drwtNo6 = hashMap["no6"].toString().toInt(),
                                bnusNo = hashMap["bonusNo"].toString().toInt(),
                                firstAccumamnt = hashMap["winnerTotal"].toString().toLong(),
                                firstWinamnt = hashMap["winnerReward"].toString().toLong(),
                                firstPrzwnerCo = hashMap["winnerCount"].toString().toInt(),
                                drwNoDate = hashMap["date"].toString()
                            )
                            listCurrent.add(lotto)
                        }
                    }

                    callback(listCurrent)
                }
            }
        })
    }

    override fun setLottoList(list: List<LottoDto>) {
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
