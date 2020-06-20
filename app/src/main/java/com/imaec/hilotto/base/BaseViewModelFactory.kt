package com.imaec.hilotto.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imaec.hilotto.viewmodel.*

class BaseViewModelFactory(private vararg val repository: Any) : ViewModelProvider.Factory {

    private val TAG = this::class.java.simpleName

    /**
     * ViewModel을 생성
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel() as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel() as T
            modelClass.isAssignableFrom(StatisticsViewModel::class.java) -> StatisticsViewModel() as T
            modelClass.isAssignableFrom(RecommendViewModel::class.java) -> RecommendViewModel() as T
            modelClass.isAssignableFrom(MyViewModel::class.java) -> MyViewModel() as T
            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}