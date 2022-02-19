package com.imaec.hilotto.base

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    protected val TAG = this::class.java.simpleName

    lateinit var adapter: BaseAdapter
}
