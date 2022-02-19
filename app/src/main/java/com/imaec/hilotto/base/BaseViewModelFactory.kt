package com.imaec.hilotto.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imaec.hilotto.repository.FirebaseRepository
import com.imaec.hilotto.repository.LottoRepository
import com.imaec.hilotto.repository.NumberRepository
import com.imaec.hilotto.viewmodel.ContinuesViewModel
import com.imaec.hilotto.viewmodel.EditNumberViewModel
import com.imaec.hilotto.viewmodel.HomeViewModel
import com.imaec.hilotto.viewmodel.LatelyResultViewModel
import com.imaec.hilotto.viewmodel.LottoViewModel
import com.imaec.hilotto.viewmodel.MainViewModel
import com.imaec.hilotto.viewmodel.MyViewModel
import com.imaec.hilotto.viewmodel.OddEvenViewModel
import com.imaec.hilotto.viewmodel.PickViewModel
import com.imaec.hilotto.viewmodel.RecommendViewModel
import com.imaec.hilotto.viewmodel.SettingViewModel
import com.imaec.hilotto.viewmodel.StatisticsViewModel
import com.imaec.hilotto.viewmodel.StoreViewModel
import com.imaec.hilotto.viewmodel.SumViewModel
import com.imaec.hilotto.viewmodel.WinHistoryViewModel
import com.imaec.hilotto.viewmodel.WinViewModel

class BaseViewModelFactory(private vararg val repository: Any) : ViewModelProvider.Factory {

    private val TAG = this::class.java.simpleName

    /**
     * ViewModel을 생성
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LottoViewModel::class.java) -> LottoViewModel(repository[0] as LottoRepository, repository[1] as FirebaseRepository) as T
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel() as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel() as T
            modelClass.isAssignableFrom(StatisticsViewModel::class.java) -> StatisticsViewModel() as T
            modelClass.isAssignableFrom(RecommendViewModel::class.java) -> RecommendViewModel(repository[0] as NumberRepository) as T
            modelClass.isAssignableFrom(MyViewModel::class.java) -> MyViewModel(repository[0] as NumberRepository) as T
            modelClass.isAssignableFrom(SettingViewModel::class.java) -> SettingViewModel() as T
            modelClass.isAssignableFrom(LatelyResultViewModel::class.java) -> LatelyResultViewModel() as T
            modelClass.isAssignableFrom(StoreViewModel::class.java) -> StoreViewModel() as T
            modelClass.isAssignableFrom(WinHistoryViewModel::class.java) -> WinHistoryViewModel() as T
            modelClass.isAssignableFrom(EditNumberViewModel::class.java) -> EditNumberViewModel(repository[0] as NumberRepository) as T
            modelClass.isAssignableFrom(SumViewModel::class.java) -> SumViewModel() as T
            modelClass.isAssignableFrom(PickViewModel::class.java) -> PickViewModel() as T
            modelClass.isAssignableFrom(ContinuesViewModel::class.java) -> ContinuesViewModel() as T
            modelClass.isAssignableFrom(OddEvenViewModel::class.java) -> OddEvenViewModel() as T
            modelClass.isAssignableFrom(WinViewModel::class.java) -> WinViewModel() as T
            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}
