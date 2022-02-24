package com.imaec.hilotto.ui.setting

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.imaec.domain.model.MyNumberDto
import com.imaec.domain.successOr
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.domain.usecase.number.InsertAllUseCase
import com.imaec.domain.usecase.number.SelectAllListUseCase
import com.imaec.domain.usecase.preferences.GetStatisticsRoundUserCase
import com.imaec.domain.usecase.preferences.SetStatisticsRoundUserCase
import com.imaec.hilotto.model.MyNumberVo
import com.imaec.hilotto.model.MyNumberVo.Companion.dtoToVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val selectAllListUseCase: SelectAllListUseCase,
    private val insertAllUseCase: InsertAllUseCase,
    private val getStatisticsRoundUserCase: GetStatisticsRoundUserCase,
    private val setStatisticsRoundUserCase: SetStatisticsRoundUserCase
) : BaseViewModel() {

    private val _state = MutableLiveData<SettingState>()
    val state: LiveData<SettingState> get() = _state

    private val _appVersion = MutableLiveData("")
    val appVersion: LiveData<String> get() = _appVersion

    private val _settingStatistics = MutableLiveData("20회")
    val settingStatistics: LiveData<String> get() = _settingStatistics

    var numberList = emptyList<MyNumberVo>()

    init {
        viewModelScope.launch {
            _settingStatistics.value = "${getStatisticsRoundUserCase()}회"
            numberList = selectAllListUseCase().successOr(emptyList()).map(::dtoToVo)
        }
    }

    fun setAppVersion(appVersion: String) {
        _appVersion.value = appVersion
    }

    fun setSettingStatistics(settingStatistics: Int) {
        viewModelScope.launch {
            setStatisticsRoundUserCase(settingStatistics)
        }
        _settingStatistics.value = "${settingStatistics}회"
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

    fun export(path: String) {
        _state.value = when {
            numberList.isEmpty() -> SettingState.ShowToast("내 번호가 없습니다.")
            Build.VERSION.SDK_INT >= 30 -> SettingState.ExportStep2
            else -> {
                val file = File(path).takeIf {
                    !it.exists()
                }?.mkdir()

                try {
                    with(BufferedWriter(FileWriter("$file/myNumber.json", false))) {
                        write(Gson().toJson(numberList))
                        flush()
                        close()
                    }
                    SettingState.ShowToast("내 번호를 내부저장소에 저장하였습니다.")
                } catch (e: IOException) {
                    SettingState.ShowToast(
                        "내 번호 내보내기에 실패하였습니다.\n관리자에게 문의해주시기 바랍니다."
                    )
                }
            }
        }
    }

    fun import() {
        _state.value = SettingState.ImportStep2
    }

    fun import(inputStream: InputStream?) {
        _state.value = try {
            val bufferReader = BufferedReader(InputStreamReader(inputStream!!))

            val stringBuilder = StringBuilder()
            var line: String?
            while (bufferReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            val list = Gson().fromJson(stringBuilder.toString(), Array<MyNumberVo>::class.java)
            saveNumbers(list.toList())
            SettingState.ShowToast("내 번호를 내부저장소에서 가져왔습니다.")
        } catch (e: IOException) {
            SettingState.ShowToast(
                "내 번호 가져오기에 실패하였습니다.\nDownload 폴더에 myNumber.json 파일이 있는지 확인해주세요."
            )
        }
    }

    private fun saveNumbers(list: List<MyNumberVo>) {
        if (list.isEmpty()) return

        viewModelScope.launch {
            insertAllUseCase(
                list.map {
                    MyNumberDto(
                        numberId = it.numberId,
                        number1 = it.number1,
                        number2 = it.number2,
                        number3 = it.number3,
                        number4 = it.number4,
                        number5 = it.number5,
                        number6 = it.number6
                    )
                }
            )
        }
    }

    fun onClickSettingStatistics() {
        _state.value = SettingState.OnClickSettingStatistics
    }

    fun onClickExport() {
        _state.value = SettingState.OnClickExport
    }

    fun onClickExportInfo() {
        _state.value = SettingState.OnClickExportInfo
    }

    fun onClickImport() {
        _state.value = SettingState.OnClickImport
    }

    fun onClickImportInfo() {
        _state.value = SettingState.OnClickImportInfo
    }

    fun onClickShare() {
        _state.value = SettingState.OnClickShare
    }
}
