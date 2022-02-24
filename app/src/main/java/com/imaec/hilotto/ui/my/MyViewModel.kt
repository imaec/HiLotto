package com.imaec.hilotto.ui.my

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.domain.usecase.number.DeleteByIdUseCase
import com.imaec.domain.usecase.number.SelectAllUseCase
import com.imaec.hilotto.model.FitNumberVo
import com.imaec.hilotto.model.LottoVo
import com.imaec.hilotto.model.MyNumberVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    selectAllUseCase: SelectAllUseCase,
    private val deleteByIdUseCase: DeleteByIdUseCase
) : BaseViewModel() {

    private val _state = MutableLiveData<MyState>()
    val state: LiveData<MyState> get() = _state

    val numberEntityList = selectAllUseCase.execute()

    private val _myNumberList = MutableLiveData<List<MyNumberVo>>()
    val myNumberList: LiveData<List<MyNumberVo>> get() = _myNumberList

    fun deleteNumber(myNumber: MyNumberVo) {
        viewModelScope.launch {
            deleteByIdUseCase(myNumber.numberId)
        }
    }

    fun checkWin(result: LottoVo) {
        val winNumbers = arrayOf(
            result.drwtNo1,
            result.drwtNo2,
            result.drwtNo3,
            result.drwtNo4,
            result.drwtNo5,
            result.drwtNo6
        )
        _myNumberList.value = numberEntityList.value?.map {
            val fitNumber = FitNumberVo()
            val listTemp = arrayListOf<Int>()
            arrayOf(
                it.number1,
                it.number2,
                it.number3,
                it.number4,
                it.number5,
                it.number6
            ).forEach { myNumber ->
                winNumbers.forEach { winNumber ->
                    if (winNumber == myNumber) {
                        listTemp.add(myNumber)
                    }
                }
                if (result.bnusNo == myNumber) {
                    fitNumber.numberBonus = myNumber
                }
            }
            fitNumber.apply {
                listFitNumber.addAll(listTemp)
                listFitNumber.forEach { fitNumber ->
                    if (it.number1 == fitNumber) isFitNo1 = true
                    if (it.number2 == fitNumber) isFitNo2 = true
                    if (it.number3 == fitNumber) isFitNo3 = true
                    if (it.number4 == fitNumber) isFitNo4 = true
                    if (it.number5 == fitNumber) isFitNo5 = true
                    if (it.number6 == fitNumber) isFitNo6 = true
                }
                arrayOf(
                    it.number1,
                    it.number2,
                    it.number3,
                    it.number4,
                    it.number5,
                    it.number6
                ).forEach { number ->
                    listIsFitBonus.add(numberBonus == number)
                }
                rank = when (fitNumber.listFitNumber.size) {
                    6 -> 1
                    5 -> if (fitNumber.numberBonus > 0) 2 else 3
                    4 -> 4
                    3 -> 5
                    else -> 0
                }
            }
            MyNumberVo(
                numberId = it.numberId,
                number1 = it.number1,
                number2 = it.number2,
                number3 = it.number3,
                number4 = it.number4,
                number5 = it.number5,
                number6 = it.number6,
                fitNumber = fitNumber
            )
        }
    }

    fun onClickNumber(myNumber: MyNumberVo) {
        _state.value = MyState.OnClickNumber(myNumber)
    }

    fun onLongClickNumber(myNumber: MyNumberVo): Boolean {
        _state.value = MyState.OnLongClickNumber(myNumber)
        return true
    }
}
