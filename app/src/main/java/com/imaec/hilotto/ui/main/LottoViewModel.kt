package com.imaec.hilotto.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.imaec.hilotto.URL_LOTTO
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.model.StoreDTO
import com.imaec.hilotto.domain.usecase.firebase.GetLottoListUseCase
import com.imaec.hilotto.domain.usecase.firebase.SetLottoListUseCase
import com.imaec.hilotto.domain.usecase.firebase.SetWeekUseCase
import com.imaec.hilotto.domain.usecase.lotto.GetCurDrwNoUseCase
import com.imaec.hilotto.domain.usecase.lotto.GetDataUseCase
import com.imaec.hilotto.domain.usecase.lotto.GetStoreUseCase
import com.imaec.hilotto.utils.DateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LottoViewModel @Inject constructor(
    private val getDataUseCase: GetDataUseCase,
    private val getCurDrwNoUseCase: GetCurDrwNoUseCase,
    private val getStoreUseCase: GetStoreUseCase,
    private val getLottoListUseCase: GetLottoListUseCase,
    private val setLottoListUseCase: SetLottoListUseCase,
    private val setWeekUseCase: SetWeekUseCase
) : BaseViewModel() {

    private val _curDrwNo = MutableLiveData(1)
    val curDrwNo: LiveData<Int> get() = _curDrwNo

    private val _lottoList = MutableLiveData<List<LottoDTO>>(ArrayList())
    val lottoList: LiveData<List<LottoDTO>> get() = _lottoList

    private val _curNum1 = MutableLiveData(1)
    val curNum1: LiveData<Int> get() = _curNum1

    private val _curNum2 = MutableLiveData(2)
    val curNum2: LiveData<Int> get() = _curNum2

    private val _curNum3 = MutableLiveData(3)
    val curNum3: LiveData<Int> get() = _curNum3

    private val _curNum4 = MutableLiveData(4)
    val curNum4: LiveData<Int> get() = _curNum4

    private val _curNum5 = MutableLiveData(5)
    val curNum5: LiveData<Int> get() = _curNum5

    private val _curNum6 = MutableLiveData(6)
    val curNum6: LiveData<Int> get() = _curNum6

    private val _curNumBonus = MutableLiveData(45)
    val curNumBonus: LiveData<Int> get() = _curNumBonus

    private val _drwDate = MutableLiveData(DateUtil.getDate("yyyy-MM-dd"))
    val drwDate: LiveData<String> get() = _drwDate

    private val _winCount = MutableLiveData(0)
    val winCount: LiveData<Int> get() = _winCount

    private val _price = MutableLiveData<Long>(0)
    val price: LiveData<Long> get() = _price

    private val _storeList = MutableLiveData<List<StoreDTO>>(ArrayList())
    val storeList: LiveData<List<StoreDTO>> get() = _storeList

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
        }
    }

    private fun getDatabaseData(callback: () -> Unit) {
        viewModelScope.launch {
            getLottoListUseCase {
                _lottoList.value = it
                setCurData(it[0])
                callback()
            }
        }
    }

    private fun getLottoSiteData(
        curDrwNoReal: Int,
        curDrwNo: Int,
        callback: (Boolean) -> Unit,
        callbackProgress: (Int) -> Unit
    ) {
        val listTemp = ArrayList<LottoDTO>()
        val gap = curDrwNoReal - curDrwNo
        for (drwNo in (curDrwNo)..curDrwNoReal) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    getDataUseCase(
                        Triple(
                            drwNo,
                            {
                                listTemp.add(it)
                                callbackProgress((listTemp.size * 100) / curDrwNoReal)

                                if (listTemp.size == gap + 1) {
                                    // 모든 리스트 DB에 저장
                                    saveLottoList(
                                        curDrwNoReal,
                                        listTemp.sortedBy { dto -> dto.drwNo }
                                    )

                                    _lottoList.value =
                                        listTemp.sortedByDescending { dto -> dto.drwNo }
                                    setCurData(listTemp.sortedByDescending { dto -> dto.drwNo }[0])
                                    callback(true)

                                    getDatabaseData {
                                        callback(true)
                                    }
                                }
                            },
                            {
                                callback(false)
                            }
                        )
                    )
                }
            }
        }
    }

    private fun saveLottoList(curDrwNoReal: Int, lottoList: List<LottoDTO>) {
        viewModelScope.launch {
            setWeekUseCase(curDrwNoReal)
            setLottoListUseCase(lottoList)
        }
    }

    private fun getCurDrwNo(callback: (Int) -> Unit) {
        viewModelScope.launch {
            getCurDrwNoUseCase(
                Pair(
                    URL_LOTTO,
                    { curDrwNo ->
                        launch(Dispatchers.Main) { callback(curDrwNo) }
                    }
                )
            )
        }
    }

    fun getLotto(curDrwNo: Int, callback: (Boolean) -> Unit, callbackProgress: (Int) -> Unit) {
        getCurDrwNo {
            _curDrwNo.value = it
            viewModelScope.launch {
                setWeekUseCase(it)
            }
            if (it == curDrwNo) {
                getDatabaseData {
                    callback(true)
                }
            } else if (it > curDrwNo) {
                getLottoSiteData(it, curDrwNo, callback, callbackProgress)
            }
        }
    }

    fun getStore(drwNo: Int) {
        viewModelScope.launch {
            getStoreUseCase(
                Pair(
                    drwNo,
                    {
                        launch(Dispatchers.Main) { _storeList.value = it }
                    }
                )
            )
        }
    }
}
