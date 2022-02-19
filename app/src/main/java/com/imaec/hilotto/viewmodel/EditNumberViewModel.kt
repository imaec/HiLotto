package com.imaec.hilotto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.domain.successOr
import com.imaec.hilotto.domain.usecase.number.UpdateUseCase
import com.imaec.hilotto.room.entity.NumberEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNumberViewModel @Inject constructor(
    private val updateUseCase: UpdateUseCase
) : BaseViewModel() {

    private val _isVisible = MutableLiveData<Boolean>(false)
    val isVisible: LiveData<Boolean> get() = _isVisible

    private val _numberEntity = MutableLiveData<NumberEntity>(NumberEntity())
    val numberEntity: LiveData<NumberEntity> get() = _numberEntity

    private val _number = MutableLiveData<Number>(Number())
    val number: LiveData<Number> get() = _number

    private fun sortNumber() {
        val listTemp = arrayListOf(
            _numberEntity.value!!.number1,
            _numberEntity.value!!.number2,
            _numberEntity.value!!.number3,
            _numberEntity.value!!.number4,
            _numberEntity.value!!.number5,
            _numberEntity.value!!.number6
        )
        listTemp.sort()
        _numberEntity.value = NumberEntity(
            _numberEntity.value!!.numberId,
            listTemp[0],
            listTemp[1],
            listTemp[2],
            listTemp[3],
            listTemp[4],
            listTemp[5]
        )
    }

    fun setVisible(visible: Boolean) {
        _isVisible.value = visible
    }

    fun setNumber(entity: NumberEntity) {
        _numberEntity.value = entity
        if (_number.value!!.number1 == 0) {
            _number.value = Number(
                entity.number1,
                entity.number2,
                entity.number3,
                entity.number4,
                entity.number5,
                entity.number6
            )
        }
    }

    fun update(callback: (Boolean) -> Unit) {
        sortNumber()
        viewModelScope.launch {
            callback(updateUseCase(numberEntity.value!!).successOr(false))
        }
    }

    inner class Number(
        var number1: Int = 0,
        var number2: Int = 0,
        var number3: Int = 0,
        var number4: Int = 0,
        var number5: Int = 0,
        var number6: Int = 0
    )
}
