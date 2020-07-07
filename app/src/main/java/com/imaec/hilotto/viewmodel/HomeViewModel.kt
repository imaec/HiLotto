package com.imaec.hilotto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.repository.LottoRepository
import com.imaec.hilotto.utils.DateUtil
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: LottoRepository
) : BaseViewModel() {

    private val _curDrwNo = MutableLiveData<Int>().set(1)
    val curDrwNo: LiveData<Int>
        get() = _curDrwNo

    private val _listResult = MutableLiveData<List<LottoDTO>>().set(ArrayList())
    val listResult: LiveData<List<LottoDTO>>
        get() = _listResult

    private val _curNum1 = MutableLiveData<Int>().set(1)
    val curNum1: LiveData<Int>
        get() = _curNum1

    private val _curNum2 = MutableLiveData<Int>().set(2)
    val curNum2: LiveData<Int>
        get() = _curNum2

    private val _curNum3 = MutableLiveData<Int>().set(3)
    val curNum3: LiveData<Int>
        get() = _curNum3

    private val _curNum4 = MutableLiveData<Int>().set(4)
    val curNum4: LiveData<Int>
        get() = _curNum4

    private val _curNum5 = MutableLiveData<Int>().set(5)
    val curNum5: LiveData<Int>
        get() = _curNum5

    private val _curNum6 = MutableLiveData<Int>().set(6)
    val curNum6: LiveData<Int>
        get() = _curNum6

    private val _curNumBonus = MutableLiveData<Int>().set(45)
    val curNumBonus: LiveData<Int>
        get() = _curNumBonus

    private val _drwDate = MutableLiveData<String>().set(DateUtil.getDate("yyyy-MM-dd"))
    val drwDate: LiveData<String>
        get() = _drwDate

    private val _winCount = MutableLiveData<Int>().set(0)
    val winCount: LiveData<Int>
        get() = _winCount

    private val _price = MutableLiveData<Long>().set(0)
    val price: LiveData<Long>
        get() = _price

    private fun initData(curDrwNoApp: Int, curDrwNoReal: Int, callback: (List<LottoDTO>?) -> Unit) {
        val gap = curDrwNoReal - curDrwNoApp + 1
        // 최신 회차 DB에 저장
        addDataOnFireStore("lotto_data", "week", hashMapOf("cur_week" to curDrwNoReal))
        val listTemp = ArrayList<LottoDTO>()
        for (drwNo in curDrwNoApp..curDrwNoReal) {
            repository.getData(drwNo, {
                // onResponse
                listTemp.add(it)
                // 마지막 아이템이 담겨짐
                if (listTemp.size == gap) {
                    // 모든 리스트 DB에 저장
                    // Todo FireStore List에 추가하는 방법 찾아보기
                    listTemp.sortBy { dto ->
                        dto.drwNo
                    }
                    addDataOnFireStore("lotto_data", "result", hashMapOf("list_result" to listTemp), {
                        _curDrwNo.value = curDrwNoReal
                        _listResult.value = listTemp
                        setCurData(listTemp[gap-1])

                        callback(listTemp)
                    })
                }
            }, {
                // onFailure
                callback(null)
            })
        }
    }

    private fun setCurData(dto: LottoDTO) {
        dto.apply {
            _curNum1.value = drwtNo1
            _curNum2.value = drwtNo2
            _curNum3.value = drwtNo3
            _curNum4.value = drwtNo4
            _curNum5.value = drwtNo5
            _curNum6.value = drwtNo6
            _curNumBonus.value = bnusNo
            _drwDate.value = drwNoDate
            _winCount.value = firstPrzwnerCo
            _price.value = firstWinamnt
        }
    }

    fun checkLotto(curDrwNo: Int, callback: (List<LottoDTO>?) -> Unit) {
        val parsingUrl = "https://www.dhlottery.co.kr/common.do?method=main&mainMode=default"
        viewModelScope.launch {
            repository.getCurDrwNo(parsingUrl) {
                if (curDrwNo == it) {
                    // DB에 저장된 데이터 가져오기
                } else {
                    // 최신 데이터 DB에 저장
                    initData(curDrwNo, it, callback)
                }
            }
        }
    }
}