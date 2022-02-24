package com.imaec.hilotto.ui.editnumber

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.imaec.domain.model.MyNumberDto
import com.imaec.domain.successOr
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.domain.usecase.number.UpdateUseCase
import com.imaec.hilotto.model.MyNumberVo
import com.imaec.hilotto.ui.editnumber.EditNumberActivity.Companion.MY_NUMBER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNumberViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val updateUseCase: UpdateUseCase
) : BaseViewModel() {

    private val _state = MutableLiveData<EditNumberState>()
    val state: LiveData<EditNumberState> get() = _state

    private val _visibleBg = MutableLiveData(false)
    val visibleBg: LiveData<Boolean> get() = _visibleBg

    private val _myNumber = MutableLiveData<MyNumberVo>(savedStateHandle.get(MY_NUMBER))
    val myNumber: LiveData<MyNumberVo> get() = _myNumber

    private val _number = MediatorLiveData<Number>().apply {
        addSource(myNumber) {
            value = if (number.value == null) {
                Number(it.number1, it.number2, it.number3, it.number4, it.number5, it.number6)
            } else {
                number.value
            }
        }
    }
    val number: LiveData<Number> get() = _number

    private var selectedIndex = -1
    private var pickedNumber = 1

    fun setPickedNumber(number: Int) {
        this.pickedNumber = number
    }

    fun setVisibleBg(visible: Boolean) {
        _visibleBg.value = visible
    }

    fun checkNumber(): Boolean {
        this.myNumber.value?.let {
            val numbers = mutableListOf(
                it.number1,
                it.number2,
                it.number3,
                it.number4,
                it.number5,
                it.number6
            )
            numbers.forEachIndexed { index, number ->
                if (selectedIndex != index) {
                    if (pickedNumber == number) return false
                }
            }
        }
        setNumber()
        return true
    }

    private fun setNumber() {
        myNumber.value?.let {
            _myNumber.value = when (selectedIndex) {
                0 -> it.copy(number1 = pickedNumber)
                1 -> it.copy(number2 = pickedNumber)
                2 -> it.copy(number3 = pickedNumber)
                3 -> it.copy(number4 = pickedNumber)
                4 -> it.copy(number5 = pickedNumber)
                5 -> it.copy(number6 = pickedNumber)
                else -> it
            }
        }
    }

    fun update(callback: (Boolean) -> Unit) {
        myNumber.value?.let { myNumber ->
            sortNumber(myNumber)
            viewModelScope.launch {
                callback(
                    updateUseCase(
                        MyNumberDto(
                            myNumber.numberId,
                            myNumber.number1,
                            myNumber.number2,
                            myNumber.number3,
                            myNumber.number4,
                            myNumber.number5,
                            myNumber.number6
                        )
                    ).successOr(false)
                )
            }
        }
    }

    private fun sortNumber(myNumber: MyNumberVo) {
        val listTemp = mutableListOf(
            myNumber.number1,
            myNumber.number2,
            myNumber.number3,
            myNumber.number4,
            myNumber.number5,
            myNumber.number6
        ).sorted()
        _myNumber.value = MyNumberVo(
            myNumber.numberId,
            listTemp[0],
            listTemp[1],
            listTemp[2],
            listTemp[3],
            listTemp[4],
            listTemp[5]
        )
    }

    fun onClickNumber(index: Int, number: Int) {
        selectedIndex = index
        _state.value = EditNumberState.OnClickNumber(index, number)
    }

    fun onClickBg() {
        _state.value = EditNumberState.OnClickBg
    }

    fun onClickOk() {
        _state.value = EditNumberState.OnClickOk
    }

    fun onClickCancel() {
        _state.value = EditNumberState.OnClickCancel
    }

    fun onClickFinish() {
        _state.value = EditNumberState.OnClickFinish
    }

    fun onClickSave() {
        _state.value = EditNumberState.OnClickSave
    }

    data class Number(
        var number1: Int = 0,
        var number2: Int = 0,
        var number3: Int = 0,
        var number4: Int = 0,
        var number5: Int = 0,
        var number6: Int = 0
    )
}
