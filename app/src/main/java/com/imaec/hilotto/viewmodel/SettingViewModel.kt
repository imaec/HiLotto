package com.imaec.hilotto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.base.BaseViewModel

class SettingViewModel : BaseViewModel() {

    private val _appVersion = MutableLiveData<String>("")
    val appVersion: LiveData<String>
        get() = _appVersion

    fun setAppVersion(appVersion: String) {
        _appVersion.value = appVersion
    }
}