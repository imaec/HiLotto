package com.imaec.hilotto.ui.my

import com.imaec.hilotto.model.MyNumberVo

sealed class MyState {

    data class OnClickNumber(val myNumber: MyNumberVo) : MyState()

    data class OnLongClickNumber(val myNumber: MyNumberVo) : MyState()
}
