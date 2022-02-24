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
import com.imaec.domain.usecase.preferences.GetRecommendAllCheckUseCase
import com.imaec.domain.usecase.preferences.GetRecommendOddEvenCheckUseCase
import com.imaec.domain.usecase.preferences.GetRecommendPickCheckUseCase
import com.imaec.domain.usecase.preferences.GetRecommendSumCheckUseCase
import com.imaec.domain.usecase.preferences.SetRecommendAllCheckUseCase
import com.imaec.domain.usecase.preferences.SetRecommendOddEvenCheckUseCase
import com.imaec.domain.usecase.preferences.SetRecommendPickCheckUseCase
import com.imaec.domain.usecase.preferences.SetRecommendSumCheckUseCase
import com.imaec.hilotto.model.NumberVo
import com.imaec.hilotto.utils.addSourceList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecommendViewModel @Inject constructor(
    private val selectByNumbersUseCase: SelectByNumbersUseCase,
    private val insertUseCase: InsertUseCase,
    private val getRecommendSumCheckUseCase: GetRecommendSumCheckUseCase,
    private val setRecommendSumCheckUseCase: SetRecommendSumCheckUseCase,
    private val getRecommendPickCheckUseCase: GetRecommendPickCheckUseCase,
    private val setRecommendPickCheckUseCase: SetRecommendPickCheckUseCase,
    private val getRecommendOddEvenCheckUseCase: GetRecommendOddEvenCheckUseCase,
    private val setRecommendOddEvenCheckUseCase: SetRecommendOddEvenCheckUseCase,
    private val getRecommendAllCheckUseCase: GetRecommendAllCheckUseCase,
    private val setRecommendAllCheckUseCase: SetRecommendAllCheckUseCase
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

    private fun setAllCheck(): Boolean {
        conditionAll.value = conditionSum.value == true &&
            conditionPick.value == true &&
            conditionOddEven.value == true
        return conditionAll.value ?: false
    }

    fun fetchCondition() {
        viewModelScope.launch {
            val isAllCheck = getRecommendAllCheckUseCase()
            val isSumCheck = getRecommendSumCheckUseCase()
            val isPickCheck = getRecommendPickCheckUseCase()
            val isOddEventCheck = getRecommendOddEvenCheckUseCase()
            conditionSum.value = if (isAllCheck) true else getRecommendSumCheckUseCase()
            conditionPick.value = if (isAllCheck) true else getRecommendPickCheckUseCase()
            conditionOddEven.value = if (isAllCheck) true else getRecommendOddEvenCheckUseCase()
            conditionAll.value = if (isAllCheck) true else isAllCheck
        }
    }

    fun onClickConditionAll() {
        val isAllCheck = conditionAll.value ?: false
        conditionSum.value = isAllCheck
        conditionPick.value = isAllCheck
        conditionOddEven.value = isAllCheck

        setAllCheck()
    }

    fun setSumCondition(isCheck: Boolean) {
        viewModelScope.launch {
            setRecommendSumCheckUseCase(isCheck)
        }
    }

    fun setPickCondition(isCheck: Boolean) {
        viewModelScope.launch {
            setRecommendPickCheckUseCase(isCheck)
        }
    }

    fun setOddEvenCondition(isCheck: Boolean) {
        viewModelScope.launch {
            setRecommendOddEvenCheckUseCase(isCheck)
        }
    }

    fun setAllCondition(isCheck: Boolean) {
        viewModelScope.launch {
            setRecommendAllCheckUseCase(isCheck)
        }
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
