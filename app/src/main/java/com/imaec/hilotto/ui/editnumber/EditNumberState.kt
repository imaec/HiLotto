package com.imaec.hilotto.ui.editnumber

sealed class EditNumberState {

    data class OnClickNumber(val index: Int, val number: Int) : EditNumberState()

    object OnClickBg : EditNumberState()

    object OnClickOk : EditNumberState()

    object OnClickCancel : EditNumberState()

    object OnClickFinish : EditNumberState()

    object OnClickSave : EditNumberState()
}
