package com.imaec.hilotto.base

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    val TAG = this::class.java.simpleName

    @Inject
    lateinit var loadingState: LoadingState

    fun showLoading() = loadingState.showLoading()

    fun hideLoading() = loadingState.hideLoading()
}
