package com.imaec.hilotto.ui.recommend

import android.view.View
import androidx.databinding.BindingAdapter
import com.imaec.hilotto.ui.bindSingleClick

@BindingAdapter(value = ["bindNumberClick", "bindNumber"], requireAll = true)
fun View.bindNumberClick(vm: RecommendViewModel?, number: String) {
    vm ?: return

    bindSingleClick {
        vm.onClickRemoveNotIncludeNumber(number)
    }
}
