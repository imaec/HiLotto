package com.imaec.hilotto.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.room.entity.NumberEntity
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException


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

    fun export(listNumber: List<NumberEntity>, file: File): Int {
        Log.d(TAG, "    ## file : $file")
        return when {
            listNumber.isEmpty() -> R.string.msg_empty_my_number
            else -> {
                if (!file.exists()) file.mkdir()

                try {
                    val jsonString = Gson().toJson(listNumber)
                    Log.d(TAG, "    ## json : $jsonString")
                    val buf = BufferedWriter(FileWriter("$file/myNumber.json", false))
                    buf.append(jsonString)
                    buf.close()
                } catch (e: IOException) {
                    return R.string.msg_fail_save_my_number
                }
                R.string.msg_success_save_my_number
            }
        }
    }

    fun import() {

    }
}