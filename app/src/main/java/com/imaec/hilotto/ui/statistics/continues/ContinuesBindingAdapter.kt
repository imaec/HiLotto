package com.imaec.hilotto.ui.statistics.continues

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imaec.hilotto.BR
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseSingleViewAdapter
import com.imaec.hilotto.model.NumberVo
import com.imaec.hilotto.ui.bindItemList

@BindingAdapter(
    value = [
        "bindContinuesList",
        "bindContinuesNumbers"
    ]
)
fun RecyclerView.bindContinues(
    numberList: List<String>,
    continuesNumbers: Array<ArrayList<Int>>
) {
    if (adapter == null) {
        val diffUtil = object : DiffUtil.ItemCallback<NumberVo>() {
            override fun areItemsTheSame(
                oldItem: NumberVo,
                newItem: NumberVo
            ): Boolean = oldItem.no == newItem.no

            override fun areContentsTheSame(
                oldItem: NumberVo,
                newItem: NumberVo
            ): Boolean = oldItem == newItem
        }

        val animator = itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }

        val listTemp = ArrayList<String>()
        continuesNumbers.forEach {
            it.forEach { number ->
                listTemp.add("$number")
            }
        }
        val itemList = numberList.map {
            NumberVo(
                no = it,
                isContinue = true,
                continuesList = listTemp
            )
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
