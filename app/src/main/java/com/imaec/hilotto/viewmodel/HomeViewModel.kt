package com.imaec.hilotto.viewmodel

import com.imaec.hilotto.base.BaseViewModel
import com.imaec.hilotto.ui.adapter.LatelyResultAdapter

class HomeViewModel : BaseViewModel() {

    init {
        adapter = LatelyResultAdapter()
    }
}