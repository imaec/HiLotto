package com.imaec.hilotto.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imaec.hilotto.model.MainViewModel

class BaseViewModelFactory(private vararg val repository: Any) : ViewModelProvider.Factory {

    private val TAG = this::class.java.simpleName

    /**
     * ViewModel을 생성
     */
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel() as T
            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}