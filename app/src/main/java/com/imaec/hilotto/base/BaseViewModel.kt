package com.imaec.hilotto.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

abstract class BaseViewModel : ViewModel() {

    protected val TAG = this::class.java.simpleName

    private val job = Job()
    protected val viewModelScope = CoroutineScope(Dispatchers.Main + job)

    lateinit var adapter: BaseAdapter

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }

    fun <T : Any> MutableLiveData<T>.set(value: T) : MutableLiveData<T> {
        this.value = value
        return this
    }
}