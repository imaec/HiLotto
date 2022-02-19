package com.imaec.hilotto.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.room.entity.NumberEntity
import java.io.*

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
                    BufferedWriter(FileWriter("$file/myNumber.json", false)).apply {
                        write(jsonString)
                        flush()
                        close()
                    }
                } catch (e: IOException) {
                    Log.e(TAG, "    ## IO Exception : ${Log.getStackTraceString(e)}")
                    return R.string.msg_fail_export_my_number
                }
                R.string.msg_success_export_my_number
            }
        }
    }

    fun export(context: Context, listNumber: List<NumberEntity>, uri: Uri): Int {
        return try {
            val jsonString = Gson().toJson(listNumber)
            val outputStream = context.contentResolver.openOutputStream(uri)
            BufferedWriter(OutputStreamWriter(outputStream!!)).apply {
                write(jsonString)
                flush()
                close()
            }
            R.string.msg_success_export_my_number
        } catch (e: IOException) {
            Log.e(TAG, "    ## IO Exception : ${Log.getStackTraceString(e)}")
            R.string.msg_fail_export_my_number
        }
    }

    fun import(context: Context, uri: Uri): Array<NumberEntity>? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val bufferReader = BufferedReader(InputStreamReader(inputStream!!))

            val stringBuilder = StringBuilder()
            var line: String?
            while (bufferReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            Gson().fromJson(stringBuilder.toString(), Array<NumberEntity>::class.java)
        } catch (e: IOException) {
            Log.e(TAG, "    ## IO Exception : ${Log.getStackTraceString(e)}")
            null
        }
    }
}
