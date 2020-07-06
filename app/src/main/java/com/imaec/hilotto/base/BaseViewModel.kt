package com.imaec.hilotto.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

abstract class BaseViewModel : ViewModel() {

    protected val TAG = this::class.java.simpleName

    private val job = Job()
    protected val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    protected val db = FirebaseFirestore.getInstance()
    protected lateinit var adapter: BaseAdapter



    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }

    fun <T : Any> MutableLiveData<T>.set(value: T) : MutableLiveData<T> {
        this.value = value
        return this
    }

    fun addDataOnFireStore(collectionPath: String, data: Any) {
        db.collection(collectionPath).add(data)
            .addOnSuccessListener {

            }
            .addOnFailureListener {

            }
    }

    fun addDataOnFireStore(collectionPath: String, documentPath: String, data: Any, onSuccess: () -> Unit = {}, onFailure: () -> Unit = {}) {
        db.collection(collectionPath).document(documentPath).set(data)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure()
            }
    }
}