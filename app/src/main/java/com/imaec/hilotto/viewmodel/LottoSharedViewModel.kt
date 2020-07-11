package com.imaec.hilotto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.repository.FireStoreRepository
import com.imaec.hilotto.repository.LottoRepository
import com.imaec.hilotto.utils.DateUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LottoSharedViewModel(
    private val lottoRepository: LottoRepository,
    private val fireStoreRepository: FireStoreRepository
) : BaseViewModel() {

    private val _curDrwNo = MutableLiveData<Int>().set(1)
    val curDrwNo: LiveData<Int> get() = _curDrwNo

    private val _listResult = MutableLiveData<List<LottoDTO>>().set(ArrayList())
    val listResult: LiveData<List<LottoDTO>> get() = _listResult

    private val _listLatelyResult = MutableLiveData<List<LottoDTO>>().set(ArrayList())
    val listLatelyResult: LiveData<List<LottoDTO>> get() = _listLatelyResult

    private val _curNum1 = MutableLiveData<Int>().set(1)
    val curNum1: LiveData<Int> get() = _curNum1

    private val _curNum2 = MutableLiveData<Int>().set(2)
    val curNum2: LiveData<Int> get() = _curNum2

    private val _curNum3 = MutableLiveData<Int>().set(3)
    val curNum3: LiveData<Int> get() = _curNum3

    private val _curNum4 = MutableLiveData<Int>().set(4)
    val curNum4: LiveData<Int> get() = _curNum4

    private val _curNum5 = MutableLiveData<Int>().set(5)
    val curNum5: LiveData<Int> get() = _curNum5

    private val _curNum6 = MutableLiveData<Int>().set(6)
    val curNum6: LiveData<Int> get() = _curNum6

    private val _curNumBonus = MutableLiveData<Int>().set(45)
    val curNumBonus: LiveData<Int> get() = _curNumBonus

    private val _drwDate = MutableLiveData<String>().set(DateUtil.getDate("yyyy-MM-dd"))
    val drwDate: LiveData<String> get() = _drwDate

    private val _winCount = MutableLiveData<Int>().set(0)
    val winCount: LiveData<Int> get() = _winCount

    private val _price = MutableLiveData<Long>().set(0)
    val price: LiveData<Long> get() = _price

    private fun getCurDrwNoReal(callback: (Boolean) -> Unit) {
        val parsingUrl = "https://www.dhlottery.co.kr/common.do?method=main&mainMode=default"
        viewModelScope.launch {
            lottoRepository.getCurDrwNo(parsingUrl) { curDrwNoReal ->
                if (curDrwNoReal > 0) {
                    getCurDrwNoDB(curDrwNoReal, callback)
                } else {
                    callback(false)
                }
            }
        }
    }

    private fun getCurDrwNoDB(curDrwNoReal: Int, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            fireStoreRepository.getWeek("lotto_data", "week", { curDrwNoDB ->
                // onSuccess
                if (curDrwNoDB == curDrwNoReal) {
                    // 데이터 가져오기
                    callback(true)
                    return@getWeek
                }
                initData(curDrwNoDB, curDrwNoReal) { list ->
                    callback(list != null)
                }
            }, { errMsg ->
                // onFailure
                if (errMsg.isEmpty()) {
                    // 로또번호 가져오기
                    initData(0, curDrwNoReal) { list ->
                        callback(list != null)
                    }
                } else {
                    // 에러
                    callback(false)
                }
            })
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun initData(curDrwNoDB: Int, curDrwNoReal: Int, callback: (List<LottoDTO>?) -> Unit) {
        val gap = curDrwNoReal - curDrwNoDB
        val listTemp = ArrayList<LottoDTO>()
        viewModelScope.launch {
            // 최신 회차 DB에 저장
            (curDrwNoDB+1..curDrwNoReal).map { drwNo ->
                withContext(Dispatchers.IO) {
                    lottoRepository.getData(drwNo, {
                        // onResponse
                        listTemp.add(it)

                        // 마지막 아이템이 담겨짐
                        if (listTemp.size == gap) {
                            // 모든 리스트 DB에 저장
                            listTemp.sortBy { dto ->
                                dto.drwNo
                            }
                            fireStoreRepository.updateData("lotto_data", "result", listTemp as ArrayList<Any>, {
                                callback(listTemp)
                            }, {
                                callback(null)
                            })
                        }
                    }, {
                        // onFailure
                        callback(null)
                    })
                }
            }
        }
    }

    private fun setCurData(dto: LottoDTO) {
        dto.apply {
            _curDrwNo.value = drwNo
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

            if (firstWinamnt > 0L) fireStoreRepository.addData("lotto_data", "week", hashMapOf("cur_week" to drwNo))
        }
    }

    private fun getData(callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            fireStoreRepository.getData("lotto_data", "result", "list_result", {
                it?.let { list ->
                    val listTemp = ArrayList<LottoDTO>()
                    (list as List<*>).map { lotto ->
                        listTemp.add(Gson().fromJson(lotto.toString(), LottoDTO::class.java))
                    }

                    _listResult.value = listTemp
                    _listLatelyResult.value = listTemp.subList(listTemp.size-11, listTemp.size-2).reversed()
                    setCurData(listTemp[listTemp.size-1])
                }
                callback(true)
            }, {
                callback(false)
            })
        }
    }

    fun getLotto(callback: (Boolean) -> Unit) {
        getCurDrwNoReal {
            if (it) {
                getData(callback)
            } else {
                callback(it)
            }
        }
    }
}