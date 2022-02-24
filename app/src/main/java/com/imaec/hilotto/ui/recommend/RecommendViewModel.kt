package com.imaec.hilotto.ui.recommend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.imaec.domain.model.MyNumberDto
import com.imaec.domain.successOr
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.domain.usecase.number.InsertUseCase
import com.imaec.hilotto.model.LottoVo
import com.imaec.domain.usecase.number.SelectByNumbersUseCase
import com.imaec.hilotto.model.NumberVo
import com.imaec.hilotto.utils.addSourceList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendViewModel @Inject constructor(
    private val selectByNumbersUseCase: SelectByNumbersUseCase,
    private val insertUseCase: InsertUseCase
) : BaseViewModel() {

    private val _state = MutableLiveData<RecommendState>()
    val state: LiveData<RecommendState> get() = _state

    val conditionSum = MutableLiveData(false)
    val conditionPick = MutableLiveData(false)
    val conditionOddEven = MutableLiveData(false)
    val conditionAll = MediatorLiveData<Boolean>().apply {
        addSourceList(conditionSum, conditionPick, conditionOddEven) {
            setAllCheck()
        }
    }

    private val _visibleBg = MutableLiveData(false)
    val visibleBg: LiveData<Boolean> get() = _visibleBg

    private val _listIncludeNumber = MutableLiveData(arrayListOf("", "", "", "", "", ""))
    val listIncludeNumber: LiveData<ArrayList<String>> get() = _listIncludeNumber

    private val _listNotIncludeNumber = MutableLiveData<List<NumberVo>>(emptyList())
    val listNotIncludeNumber: LiveData<List<NumberVo>> get() = _listNotIncludeNumber

    private val _lottoList = MutableLiveData<List<LottoVo>>(ArrayList())
    val lottoList: LiveData<List<LottoVo>> get() = _lottoList

    fun changeAllChecked(allChecked: Boolean) {
        conditionSum.value = allChecked
        conditionPick.value = allChecked
        conditionOddEven.value = allChecked

        setAllCheck()
    }

    fun setAllCheck(): Boolean {
        conditionAll.value = conditionSum.value == true &&
            conditionPick.value == true &&
            conditionOddEven.value == true
        return conditionAll.value ?: false
    }

    fun setCondition(
        isSumCheck: Boolean,
        isPickCheck: Boolean,
        isOddEvenCheck: Boolean,
        isAllCheck: Boolean
    ) {
        conditionSum.value = if (isAllCheck) true else isSumCheck
        conditionPick.value = if (isAllCheck) true else isPickCheck
        conditionOddEven.value = if (isAllCheck) true else isOddEvenCheck
        conditionAll.value = if (isAllCheck) true else isAllCheck
    }

    fun onClickConditionAll() {
        changeAllChecked(conditionAll.value ?: false)
    }

    fun setLottoList(lottoList: List<LottoVo>) {
        _lottoList.value = lottoList
    }

    fun setVisibleBg(visible: Boolean) {
        _visibleBg.value = visible
    }

    fun checkIncludeNumber(number: String): Boolean {
        _listIncludeNumber.value?.let {
            it.forEach { includeNumber ->
                if (includeNumber == number) return false
            }
        }
        return true
    }

    fun checkNotIncludeNumber(number: String): Boolean {
        _listNotIncludeNumber.value?.let {
            it.forEach { notIncludeNumber ->
                if (notIncludeNumber.no == number) return false
            }
        }
        return true
    }

    fun setIncludeNumber(index: Int, number: String) {
        val listTemp = _listIncludeNumber.value!!
        listTemp[index] = number
        _listIncludeNumber.value = listTemp
    }

    fun removeIncludeNumber(index: Int) {
        val listTemp = _listIncludeNumber.value!!
        listTemp.removeAt(index)
        listTemp.add("")
        _listIncludeNumber.value = listTemp
    }

    fun addNotIncludeNumber(number: String) {
        val listTemp = _listNotIncludeNumber.value!!.toMutableList()
        listTemp.forEach {
            if (it.no == number) return
        }
        listTemp.add(NumberVo(no = number))
        _listNotIncludeNumber.value = listTemp
    }

    fun removeNotIncludeNumber(number: String) {
        val listTemp = _listNotIncludeNumber.value!!.toMutableList()
        var removeIndex = -1
        listTemp.forEachIndexed { index, NumberVo ->
            if (NumberVo.no == number) {
                removeIndex = index
                return@forEachIndexed
            }
        }
        removeIndex.takeIf { it > -1 }?.let {
            listTemp.removeAt(it)
        }
        _listNotIncludeNumber.value = listTemp
    }

    fun saveNumber(dto: MyNumberDto, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            if (selectByNumbersUseCase(dto).successOr(0) > 0) {
                // ALREADY EXIST
                callback(false)
                return@launch
            }
            insertUseCase(dto)
            callback(true)
            _listIncludeNumber.value = arrayListOf("", "", "", "", "", "")
        }
    }

    fun onClickInclude() {
        _state.value = RecommendState.OnClickInclude
    }

    fun onClickNotInclude() {
        _state.value = RecommendState.OnClickNotInclude
    }

    fun onClickNumber(index: Int) {
        _state.value = RecommendState.OnClickNumber(
            listIncludeNumber.value?.get(index).isNullOrEmpty(),
            index
        )
    }

    fun onClickRemoveNotIncludeNumber(notIncludeNumb: String) {
        _state.value = RecommendState.OnClickRemoveNotIncludeNumber(notIncludeNumb)
    }

    fun onClickBg() {
        _state.value = RecommendState.OnClickBg
    }

    fun onClickOk() {
        _state.value = RecommendState.OnClickOk
    }

    fun onClickCancel() {
        _state.value = RecommendState.OnClickCancel
    }

    fun onClickCreate() {
        _state.value = RecommendState.OnClickCreate
    }

    fun onClickSave() {
        _state.value = RecommendState.OnClickSave
    }

    fun getNotIncludeList() = listNotIncludeNumber.value?.map {
        it.no
    } ?: emptyList()
}
