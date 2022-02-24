package com.imaec.hilotto.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.imaec.domain.data
import com.imaec.domain.model.LottoDto
import com.imaec.domain.successOr
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.LottoVo
import com.imaec.hilotto.model.StoreVo
import com.imaec.domain.usecase.firebase.GetLottoListUseCase
import com.imaec.domain.usecase.firebase.SetLottoListUseCase
import com.imaec.domain.usecase.firebase.SetWeekUseCase
import com.imaec.domain.usecase.lotto.GetCurDrwNoUseCase
import com.imaec.domain.usecase.lotto.GetDataUseCase
import com.imaec.domain.usecase.lotto.GetStoreUseCase
import com.imaec.domain.usecase.preferences.GetLocalCurDrwNoUserCase
import com.imaec.domain.usecase.preferences.GetStatisticsRoundUserCase
import com.imaec.domain.usecase.preferences.SetLocalCurDrwNoUserCase
import com.imaec.hilotto.model.LottoVo.Companion.dtoToVo
import com.imaec.hilotto.utils.DateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LottoViewModel @Inject constructor(
    private val getDataUseCase: GetDataUseCase,
    private val getLocalCurDrwNoUserCase: GetLocalCurDrwNoUserCase,
    private val setLocalCurDrwNoUserCase: SetLocalCurDrwNoUserCase,
    private val getCurDrwNoUseCase: GetCurDrwNoUseCase,
    private val getStoreUseCase: GetStoreUseCase,
    private val getLottoListUseCase: GetLottoListUseCase,
    private val setLottoListUseCase: SetLottoListUseCase,
    private val setWeekUseCase: SetWeekUseCase,
    private val getStatisticsRoundUserCase: GetStatisticsRoundUserCase
) : BaseViewModel() {

    private val _curDrwNo = MutableLiveData(1)
    val curDrwNo: LiveData<Int> get() = _curDrwNo

    private val _lottoList = MutableLiveData<List<LottoVo>>(ArrayList())
    val lottoList: LiveData<List<LottoVo>> get() = _lottoList

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

    private val _storeList = MutableLiveData<List<StoreVo>>(ArrayList())
    val storeList: LiveData<List<StoreVo>> get() = _storeList

    private val _statisticsNo = MutableLiveData(20)
    val statisticsNo: LiveData<Int> get() = _statisticsNo

    private fun setCurData(dto: LottoVo) {
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
            _statisticsNo.value = getStatisticsRoundUserCase()
            getLottoListUseCase {
                val lottoList = it.map(::dtoToVo)
                _lottoList.value = lottoList
                setCurData(lottoList[0])
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
        val listTemp = ArrayList<LottoVo>()
        val gap = curDrwNoReal - curDrwNo
        for (drwNo in (curDrwNo)..curDrwNoReal) {
            viewModelScope.launch {
                getDataUseCase(drwNo).successOr(null)?.let {
                    listTemp.add(dtoToVo(it))
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
                } ?: run {
                    callback(false)
                }
//                    Triple(
//                        drwNo,
//                        {
//                            listTemp.add(dtoToVo(it))
//                            callbackProgress((listTemp.size * 100) / curDrwNoReal)
//
//                            if (listTemp.size == gap + 1) {
//                                // 모든 리스트 DB에 저장
//                                saveLottoList(
//                                    curDrwNoReal,
//                                    listTemp.sortedBy { dto -> dto.drwNo }
//                                )
//
//                                _lottoList.value =
//                                    listTemp.sortedByDescending { dto -> dto.drwNo }
//                                setCurData(listTemp.sortedByDescending { dto -> dto.drwNo }[0])
//                                callback(true)
//
//                                getDatabaseData {
//                                    callback(true)
//                                }
//                            }
//                        },
//                        {
//                            callback(false)
//                        }
//                    )
//                )
            }
        }
    }

    private fun saveLottoList(curDrwNoReal: Int, lottoList: List<LottoVo>) {
        viewModelScope.launch {
            setWeekUseCase(curDrwNoReal)
            setLottoListUseCase(
                lottoList.map {
                    LottoDto(
                        bnusNo = it.bnusNo,
                        drwNo = it.drwNo,
                        drwNoDate = it.drwNoDate,
                        drwtNo1 = it.drwtNo1,
                        drwtNo2 = it.drwtNo2,
                        drwtNo3 = it.drwtNo3,
                        drwtNo4 = it.drwtNo4,
                        drwtNo5 = it.drwtNo5,
                        drwtNo6 = it.drwtNo6,
                        firstAccumamnt = it.firstAccumamnt,
                        firstPrzwnerCo = it.firstPrzwnerCo,
                        firstWinamnt = it.firstWinamnt,
                        returnValue = it.returnValue,
                        totSellamnt = it.totSellamnt
                    )
                }
            )
        }
    }

    fun getLotto(callback: (Boolean) -> Unit, callbackProgress: (Int) -> Unit) {
        viewModelScope.launch {
            val curDrwNo = getLocalCurDrwNoUserCase()
            val realCurDrwNo = getCurDrwNoUseCase().data ?: curDrwNo
            setLocalCurDrwNoUserCase(realCurDrwNo)
            _curDrwNo.value = realCurDrwNo
            setWeekUseCase(realCurDrwNo)
            if (realCurDrwNo == curDrwNo) {
                getDatabaseData {
                    callback(true)
                }
            } else if (realCurDrwNo > curDrwNo) {
                getLottoSiteData(realCurDrwNo, curDrwNo, callback, callbackProgress)
            }
        }
    }

    fun getStore() {
        viewModelScope.launch {
            val drwNo = getLocalCurDrwNoUserCase()
            _storeList.value = getStoreUseCase(drwNo).data?.map(StoreVo::dtoToVo) ?: emptyList()
        }
    }
}
