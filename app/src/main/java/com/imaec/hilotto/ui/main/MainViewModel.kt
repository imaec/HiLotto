package com.imaec.hilotto.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {

    private val _settingChanged = MutableLiveData(false)
    val settingChanged: LiveData<Boolean> get() = _settingChanged

    fun init() {
        _settingChanged.value = false
    }

    fun changeSetting(changed: Boolean) {
        _settingChanged.value = changed
    }
}
