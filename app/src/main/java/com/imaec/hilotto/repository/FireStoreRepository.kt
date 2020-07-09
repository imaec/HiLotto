package com.imaec.hilotto.repository

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.imaec.hilotto.model.LottoDTO

class FireStoreRepository {

    private val db = FirebaseFirestore.getInstance()

    fun getWeek(collectionPath: String, documentPath: String, onSuccess: (Int) -> Unit = {}, onFailure: (String) -> Unit = {}) {
        db.collection(collectionPath).document(documentPath).get()
            .addOnSuccessListener { documentSnapshot ->
                documentSnapshot.data?.let {
                    onSuccess(it["cur_week"].toString().toInt())
                } ?: run {
                    onFailure("")
                }
            }
            .addOnFailureListener {
                onFailure(it.message ?: "로또 번호를 가져오는데 실패했습니다.")
            }
    }

    fun addData(collectionPath: String, documentPath: String, data: Any, onSuccess: () -> Unit = {}, onFailure: () -> Unit = {}) {
        db.collection(collectionPath).document(documentPath).set(data)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure()
            }
    }

    fun updateData(collectionPath: String, documentPath: String, data: ArrayList<Any>, onSuccess: () -> Unit = {}, onFailure: (String) -> Unit = {}) {
        db.collection(collectionPath).document(documentPath).update("list_result", FieldValue.arrayUnion(*data.toTypedArray()))
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.message ?: "로또 번호를 가져오는데 실패했습니다.")
            }
    }

    fun getData(collectionPath: String, documentPath: String, key: String, onSuccess: (Any?) -> Unit = {}, onFailure: (String) -> Unit = {}) {
        db.collection(collectionPath).document(documentPath).get()
            .addOnSuccessListener { documentSnapshot ->
                documentSnapshot.data?.let {
                    onSuccess(it[key])
                } ?: run {
                    onFailure("")
                }
            }
            .addOnFailureListener {
                onFailure(it.message ?: "로또 번호를 가져오는데 실패했습니다.")
            }
    }
}