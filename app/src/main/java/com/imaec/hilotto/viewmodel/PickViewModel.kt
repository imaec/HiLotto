package com.imaec.hilotto.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.LottoDTO

class PickViewModel : BaseViewModel() {

    private val _listPick = MutableLiveData<List<LottoDTO>>().set(ArrayList())
    val listPick: LiveData<List<LottoDTO>> get() = _listPick

    private val _pickedRange1 = MutableLiveData<Int>().set(0)
    val pickedRange1: LiveData<Int> get() = _pickedRange1

    private val _pickedRange2 = MutableLiveData<Int>().set(0)
    val pickedRange2: LiveData<Int> get() = _pickedRange2

    private val _pickedRange3 = MutableLiveData<Int>().set(0)
    val pickedRange3: LiveData<Int> get() = _pickedRange3

    private val _pickedRange4 = MutableLiveData<Int>().set(0)
    val pickedRange4: LiveData<Int> get() = _pickedRange4

    private val _pickedRange5 = MutableLiveData<Int>().set(0)
    val pickedRange5: LiveData<Int> get() = _pickedRange5

    private val _pickMax = MutableLiveData<Int>().set(0)
    val pickMax: LiveData<Int> get() = _pickMax

    private fun initRange() {
        _pickedRange1.value = 0
        _pickedRange2.value = 0
        _pickedRange3.value = 0
        _pickedRange4.value = 0
        _pickedRange5.value = 0
    }

    private fun setRange(number: Int) {
        when (number) {
            in 1..10 -> {
                _pickedRange1.value?.let {
                    _pickedRange1.value = it + 1
                }
            }
            in 11..20 -> {
                _pickedRange2.value?.let {
                    _pickedRange2.value = it + 1
                }
            }
            in 21..30 -> {
                _pickedRange3.value?.let {
                    _pickedRange3.value = it + 1
                }
            }
            in 31..40 -> {
                _pickedRange4.value?.let {
                    _pickedRange4.value = it + 1
                }
            }
            in 41..50 -> {
                _pickedRange5.value?.let {
                    _pickedRange5.value = it + 1
                }
            }
        }
    }

    private fun getPickMax() : Int {
        val listTemp = arrayListOf(_pickedRange1.value!!, _pickedRange2.value!!, _pickedRange3.value!!, _pickedRange4.value!!, _pickedRange5.value!!).sortedDescending()
        listTemp.forEach {
            Log.d(TAG, "    ## $it")
        }
        return listTemp[0]
    }

    fun setPickedNum(listResult: List<LottoDTO>, isInclude: Boolean = false) {
        initRange()
        listResult.subList(listResult.size - 21, listResult.size - 1).reversed().forEach {
            setRange(it.drwtNo1)
            setRange(it.drwtNo2)
            setRange(it.drwtNo3)
            setRange(it.drwtNo4)
            setRange(it.drwtNo5)
            setRange(it.drwtNo6)
            if (isInclude) setRange(it.bnusNo)
        }
        _pickMax.value = getPickMax()
    }
}