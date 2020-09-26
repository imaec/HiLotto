package com.imaec.hilotto.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.base.BaseViewModel

class SettingViewModel : BaseViewModel() {

    private val _appVersion = MutableLiveData<String>("")
    val appVersion: LiveData<String> get() = _appVersion

    private val _settingStatistics = MutableLiveData<String>("20회")
    val settingStatistics: LiveData<String> get() = _settingStatistics

    fun setAppVersion(appVersion: String) {
        _appVersion.value = appVersion
    }

    fun setSettingStatistics(settingStatistics: String) {
        _settingStatistics.value = settingStatistics
    }

    fun checkSettingRound(keyword: String, curDrwNo: Int): String {
        return if (keyword.isEmpty()) {
            "검색할 회차를 입력해주세요."
        } else if (keyword.toInt() < 1 || keyword.toInt() > curDrwNo) {
            "검색할 회차를 1 ~ $curDrwNo 사이로 입력해주세요."
        } else {
            "OK"
        }
    }
}