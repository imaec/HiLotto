package com.imaec.hilotto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.base.BaseViewModel

class RecommendViewModel : BaseViewModel() {

    private val _isVisible = MutableLiveData<Boolean>().set(false)
    val isVisible: LiveData<Boolean> get() = _isVisible
}