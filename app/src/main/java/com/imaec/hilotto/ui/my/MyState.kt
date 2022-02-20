package com.imaec.hilotto.ui.my

import com.imaec.hilotto.model.MyNumberDTO

sealed class MyState {

    data class OnClickNumber(val myNumber: MyNumberDTO) : MyState()

    data class OnLongClickNumber(val myNumber: MyNumberDTO) : MyState()
}
