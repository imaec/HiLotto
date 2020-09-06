package com.imaec.hilotto.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.ui.adapter.NoPickAdapter

class PickViewModel : BaseViewModel() {

    val noPickAdapter1 = NoPickAdapter()
    val noPickAdapter2 = NoPickAdapter()
    val noPickAdapter3 = NoPickAdapter()
    val noPickAdapter4 = NoPickAdapter()
    val noPickAdapter5 = NoPickAdapter()

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

    private val _listNoPick1 = MutableLiveData<List<Int>>().set(ArrayList())
    val listNoPick1: LiveData<List<Int>> get() = _listNoPick1

    private val _listNoPick2 = MutableLiveData<List<Int>>().set(ArrayList())
    val listNoPick2: LiveData<List<Int>> get() = _listNoPick2

    private val _listNoPick3 = MutableLiveData<List<Int>>().set(ArrayList())
    val listNoPick3: LiveData<List<Int>> get() = _listNoPick3

    private val _listNoPick4 = MutableLiveData<List<Int>>().set(ArrayList())
    val listNoPick4: LiveData<List<Int>> get() = _listNoPick4

    private val _listNoPick5 = MutableLiveData<List<Int>>().set(ArrayList())
    val listNoPick5: LiveData<List<Int>> get() = _listNoPick5

    private lateinit var listNoPick: ArrayList<Int>

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
        return arrayListOf(_pickedRange1.value!!, _pickedRange2.value!!, _pickedRange3.value!!, _pickedRange4.value!!, _pickedRange5.value!!).sortedDescending()[0]
    }

    private fun initNoPickNum() {
        listNoPick = ArrayList()
        (1..45).forEach {
            listNoPick.add(it)
        }
    }

    private fun setNoPickNum() {
        val listTemp1 = ArrayList<Int>()
        val listTemp2 = ArrayList<Int>()
        val listTemp3 = ArrayList<Int>()
        val listTemp4 = ArrayList<Int>()
        val listTemp5 = ArrayList<Int>()
        listNoPick.forEach { number ->
            Log.d(TAG, "    ## number : $number")
            when (number) {
                in 1..10 -> listTemp1.add(number)
                in 11..20 -> listTemp2.add(number)
                in 21..30 -> listTemp3.add(number)
                in 31..40 -> listTemp4.add(number)
                in 41..50 -> listTemp5.add(number)
            }
        }
        _listNoPick1.value = listTemp1
        _listNoPick2.value = listTemp2
        _listNoPick3.value = listTemp3
        _listNoPick4.value = listTemp4
        _listNoPick5.value = listTemp5
    }

    fun setPickedNum(listResult: List<LottoDTO>, isInclude: Boolean = false) {
        initRange()
        listResult.subList(0, 20).forEach {
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

    fun setNoPickNum(listResult: List<LottoDTO>, isInclude: Boolean = false) {
        initNoPickNum()
        listResult.subList(0, 20).forEach {
            listNoPick.remove(it.drwtNo1)
            listNoPick.remove(it.drwtNo2)
            listNoPick.remove(it.drwtNo3)
            listNoPick.remove(it.drwtNo4)
            listNoPick.remove(it.drwtNo5)
            listNoPick.remove(it.drwtNo6)
            if (isInclude) listNoPick.remove(it.bnusNo)
        }
        setNoPickNum()
    }
}