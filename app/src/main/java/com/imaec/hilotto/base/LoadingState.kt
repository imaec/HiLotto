package com.imaec.hilotto.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LoadingState {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> get() = _loading

    fun showLoading() {
        _loading.value = true
    }

    fun hideLoading() {
        _loading.value = false
    }

    fun isLoading(): Boolean = loading.value ?: false
}
