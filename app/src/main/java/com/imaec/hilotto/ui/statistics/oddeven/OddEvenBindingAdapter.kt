package com.imaec.hilotto.ui.statistics.oddeven

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imaec.hilotto.BR
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseSingleViewAdapter
import com.imaec.hilotto.model.NumberDTO
import com.imaec.hilotto.ui.bindItemList

@BindingAdapter("bindOddEvenList")
fun RecyclerView.bindOddEven(numberList: List<String>) {
    if (adapter == null) {
        val diffUtil = object : DiffUtil.ItemCallback<NumberDTO>() {
            override fun areItemsTheSame(
                oldItem: NumberDTO,
                newItem: NumberDTO
            ): Boolean = oldItem.no == newItem.no

            override fun areContentsTheSame(
                oldItem: NumberDTO,
                newItem: NumberDTO
            ): Boolean = oldItem == newItem
        }

        val animator = itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }

        val itemList = numberList.map {
            NumberDTO(no = it)
        }

        adapter = BaseSingleViewAdapter(
            layoutResId = R.layout.item_number,
            bindingItemId = BR.item,
            viewModel = mapOf(),
            diffUtil = diffUtil
        )
        bindItemList(itemList)
    }
}
