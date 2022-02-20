package com.imaec.hilotto.ui.recommend

sealed class RecommendState {

    object OnClickInclude : RecommendState()

    object OnClickNotInclude : RecommendState()

    data class OnClickNumber(val isEmpty: Boolean, val index: Int) : RecommendState()

    data class OnClickRemoveNotIncludeNumber(val notIncludeNumb: String) : RecommendState()

    object OnClickOk : RecommendState()

    object OnClickCancel : RecommendState()

    object OnClickBg : RecommendState()

    object OnClickCreate : RecommendState()

    object OnClickSave : RecommendState()
}
