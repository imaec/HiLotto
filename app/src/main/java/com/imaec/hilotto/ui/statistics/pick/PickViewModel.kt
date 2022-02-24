package com.imaec.hilotto.ui.statistics.pick

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.LottoVo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PickViewModel @Inject constructor() : BaseViewModel() {

    private val _statisticsNo = MutableLiveData(20)
    val statisticsNo: LiveData<Int> get() = _statisticsNo

    private val _pickedRange1 = MutableLiveData(0)
    val pickedRange1: LiveData<Int> get() = _pickedRange1

    private val _pickedRange2 = MutableLiveData(0)
    val pickedRange2: LiveData<Int> get() = _pickedRange2

    private val _pickedRange3 = MutableLiveData(0)
    val pickedRange3: LiveData<Int> get() = _pickedRange3

    private val _pickedRange4 = MutableLiveData(0)
    val pickedRange4: LiveData<Int> get() = _pickedRange4

    private val _pickedRange5 = MutableLiveData(0)
    val pickedRange5: LiveData<Int> get() = _pickedRange5

    private val _pickMax = MutableLiveData(0)
    val pickMax: LiveData<Int> get() = _pickMax

    private val _noPickList1 = MutableLiveData<List<Int>>(ArrayList())
    val noPickList1: LiveData<List<Int>> get() = _noPickList1

    private val _noPickList2 = MutableLiveData<List<Int>>(ArrayList())
    val noPickList2: LiveData<List<Int>> get() = _noPickList2

    private val _noPickList3 = MutableLiveData<List<Int>>(ArrayList())
    val noPickList3: LiveData<List<Int>> get() = _noPickList3

    private val _noPickList4 = MutableLiveData<List<Int>>(ArrayList())
    val noPickList4: LiveData<List<Int>> get() = _noPickList4

    private val _noPickList5 = MutableLiveData<List<Int>>(ArrayList())
    val noPickList5: LiveData<List<Int>> get() = _noPickList5

    private lateinit var noPickList: ArrayList<Int>

    fun setStatisticsNo(no: Int) {
        _statisticsNo.value = no
    }

    fun setPickedNum(lottoList: List<LottoVo>?, isInclude: Boolean = false) {
        lottoList ?: return

        initRange()
        lottoList.subList(0, statisticsNo.value ?: 20).forEach {
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
                pickedRange1.value?.let {
                    _pickedRange1.value = it + 1
                }
            }
            in 11..20 -> {
                pickedRange2.value?.let {
                    _pickedRange2.value = it + 1
                }
            }
            in 21..30 -> {
                pickedRange3.value?.let {
                    _pickedRange3.value = it + 1
                }
            }
            in 31..40 -> {
                pickedRange4.value?.let {
                    _pickedRange4.value = it + 1
                }
            }
            in 41..50 -> {
                pickedRange5.value?.let {
                    _pickedRange5.value = it + 1
                }
            }
        }
    }

    private fun getPickMax(): Int {
        return listOf(
            pickedRange1.value!!,
            pickedRange2.value!!,
            pickedRange3.value!!,
            pickedRange4.value!!,
            pickedRange5.value!!
        ).maxOf { it }
    }

    fun setNoPickNum(lottoList: List<LottoVo>?, isInclude: Boolean = false) {
        lottoList ?: return

        initNoPickNum()
        lottoList.subList(0, statisticsNo.value ?: 20).forEach {
            noPickList.remove(it.drwtNo1)
            noPickList.remove(it.drwtNo2)
            noPickList.remove(it.drwtNo3)
            noPickList.remove(it.drwtNo4)
            noPickList.remove(it.drwtNo5)
            noPickList.remove(it.drwtNo6)
            if (isInclude) noPickList.remove(it.bnusNo)
        }
        setNoPickNum()
    }

    private fun initNoPickNum() {
        noPickList = ArrayList()
        (1..45).forEach {
            noPickList.add(it)
        }
    }

    private fun setNoPickNum() {
        val listTemp1 = ArrayList<Int>()
        val listTemp2 = ArrayList<Int>()
        val listTemp3 = ArrayList<Int>()
        val listTemp4 = ArrayList<Int>()
        val listTemp5 = ArrayList<Int>()
        noPickList.forEach { number ->
            when (number) {
                in 1..10 -> listTemp1.add(number)
                in 11..20 -> listTemp2.add(number)
                in 21..30 -> listTemp3.add(number)
                in 31..40 -> listTemp4.add(number)
                in 41..50 -> listTemp5.add(number)
            }
        }
        _noPickList1.value = listTemp1
        _noPickList2.value = listTemp2
        _noPickList3.value = listTemp3
        _noPickList4.value = listTemp4
        _noPickList5.value = listTemp5
    }
}
