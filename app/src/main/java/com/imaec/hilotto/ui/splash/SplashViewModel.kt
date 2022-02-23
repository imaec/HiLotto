package com.imaec.hilotto.ui.splash

import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel() {

    val progress = MutableLiveData(0)
    val loadingText = MutableLiveData("잠시만 기다려 주세요.")
}
