package com.imaec.hilotto.ui.statistics.sum

import android.graphics.PorterDuff
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.imaec.hilotto.model.SumDTO
import timber.log.Timber

@BindingAdapter(value = ["bindSumItem", "bindSumList"], requireAll = true)
fun ProgressBar.bindProgressColor(item: SumDTO, sumList: List<SumDTO>) {
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

@BindingAdapter("bindSumMax")
fun ProgressBar.bindSumMax(sumMax: Int) {
    Timber.i("  ## bindSumMax : $sumMax")
    max = sumMax
}
