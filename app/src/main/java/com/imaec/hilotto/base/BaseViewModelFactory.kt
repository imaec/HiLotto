package com.imaec.hilotto.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imaec.hilotto.repository.FirebaseRepository
import com.imaec.hilotto.repository.LottoRepository
import com.imaec.hilotto.repository.NumberRepository
import com.imaec.hilotto.viewmodel.*

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