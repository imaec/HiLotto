package com.imaec.hilotto.ui.recommend

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.switchmaterial.SwitchMaterial
import com.imaec.hilotto.R
import com.imaec.hilotto.ui.bindSingleClick
import timber.log.Timber

@BindingAdapter("bindChangeCheck")
fun SwitchMaterial.bindChangeCheck(
    viewModel: RecommendViewModel
) {
    Timber.i("  ## bindChangeClick")
    (context as? LifecycleOwner)?.let { lifecycleOwner ->
        Timber.i("  ## lifecycleOwner : $lifecycleOwner / $id")
        val liveData = when (id) {
            R.id.sm_condition_all -> viewModel.conditionAll
            R.id.sm_condition1 -> viewModel.conditionSum
            R.id.sm_condition2 -> viewModel.conditionPick
            R.id.sm_condition3 -> viewModel.conditionOddEven
            else -> null
        }
        liveData?.observe(lifecycleOwner) {
            isChecked = it
            setOnClickListener {
                if (id == R.id.sm_condition_all) viewModel.changeAllChecked(isChecked)
                else viewModel.setAllCheck()
            }
        }
    }
}

@BindingAdapter(value = ["bindNumberClick", "bindNumber"], requireAll = true)
fun View.bindNumberClick(vm: RecommendViewModel?, number: String) {
    vm ?: return

    bindSingleClick {
        vm.onClickRemoveNotIncludeNumber(number)
    }
}
