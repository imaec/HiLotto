package com.imaec.hilotto.ui.statistics.sum

import android.graphics.PorterDuff
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.imaec.hilotto.model.SumVo

@BindingAdapter(value = ["bindSumItem", "bindSumList"], requireAll = true)
fun ProgressBar.bindProgressColor(item: SumVo, sumList: List<SumVo>) {
    progressDrawable.setColorFilter(
        when (item.sum) {
            // 최대
            sumList.maxOf { it.sum } -> ContextCompat.getColor(
                context,
                android.R.color.holo_red_dark
            )
            // 최소
            sumList.minOf { it.sum } -> ContextCompat.getColor(
                context,
                android.R.color.holo_blue_dark
            )
            else -> ContextCompat.getColor(context, android.R.color.black)
        },
        PorterDuff.Mode.SRC_IN
    )
}
