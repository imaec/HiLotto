package com.imaec.hilotto.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.URL_LOTTO
import com.imaec.hilotto.URL_STORE
import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.model.StoreDTO
import com.imaec.hilotto.repository.FirebaseRepository
import com.imaec.hilotto.repository.LottoRepository
import com.imaec.hilotto.utils.DateUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LottoViewModel(
    private val lottoRepository: LottoRepository,
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel() {

    private val _curDrwNo = MutableLiveData<Int>().set(1)
    val curDrwNo: LiveData<Int> get() = _curDrwNo

    private val _listResult = MutableLiveData<List<LottoDTO>>().set(ArrayList())
    val listResult: LiveData<List<LottoDTO>> get() = _listResult

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

    private val _listStore = MutableLiveData<List<StoreDTO>>().set(ArrayList())
    val listStore: LiveData<List<StoreDTO>> get() = _listStore

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
        firebaseRepository.getLottoList {
            _listResult.value = it
            setCurData(it[0])
            callback()
        }
    }

    private fun getLottoSiteData(curDrwNoReal: Int, curDrwNo: Int, callback: (Boolean) -> Unit, callbackProgress: (Int) -> Unit) {
        val listTemp = ArrayList<LottoDTO>()
        val gap = curDrwNoReal - curDrwNo
        for (drwNo in (curDrwNo)..curDrwNoReal) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    lottoRepository.getData(drwNo, {
                        listTemp.add(it)
                        callbackProgress((listTemp.size*100)/curDrwNoReal)

                        if (listTemp.size == gap + 1) {
                            // 모든 리스트 DB에 저장
                            firebaseRepository.setWeek(curDrwNoReal)
                            firebaseRepository.setLottoList(listTemp.sortedBy { dto -> dto.drwNo })

                            _listResult.value = listTemp.sortedByDescending { dto -> dto.drwNo }
                            setCurData(listTemp.sortedByDescending { dto -> dto.drwNo }[0])
                            callback(true)

                            getDatabaseData {
                                callback(true)
                            }
                        }
                    }, {
                        callback(false)
                    })
                }
            }
        }
    }

    private fun getCurDrwNo(callback: (Int) -> Unit) {
        viewModelScope.launch {
            lottoRepository.getCurDrwNo(URL_LOTTO) { curDrwNo ->
                launch(Dispatchers.Main) { callback(curDrwNo) }
            }
        }
    }

    fun getLotto(curDrwNo: Int, callback: (Boolean) -> Unit, callbackProgress: (Int) -> Unit) {
        getCurDrwNo {
            _curDrwNo.value = it
            firebaseRepository.setWeek(it)
            if (it == curDrwNo) {
                Log.e(TAG, "    ## getLotto > true")
                // Preferences.setValue(this, getString(R.string.WEEK), curDrwNo)
                getDatabaseData {
                    callback(true)
                }
            } else if (it > curDrwNo) {
                Log.e(TAG, "    ## getLotto > false")
                getLottoSiteData(it, curDrwNo, callback, callbackProgress)
            }
        }
    }

    fun getStore() {
        viewModelScope.launch {
            lottoRepository.getStore(URL_STORE) {
                launch(Dispatchers.Main) { _listStore.value = it }
            }
        }
    }
}