package com.imaec.hilotto.ui.lately

sealed class LatelyState {

    object OnClickSearch : LatelyState()

    data class SuccessCheck(val position: Int) : LatelyState()

    data class FailCheck(val message: String) : LatelyState()
}
