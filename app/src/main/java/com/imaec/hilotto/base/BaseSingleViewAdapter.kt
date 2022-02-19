package com.imaec.hilotto.base

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class BaseSingleViewAdapter<ITEM : Any>(
    @LayoutRes private val layoutResId: Int,
    private val bindingItemId: Int,
    private val viewModel: Map<Int, ViewModel>,
    diffUtil: DiffUtil.ItemCallback<ITEM>
) : ListAdapter<ITEM, BaseViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        BaseViewHolder(
            parent = parent,
            layoutResourceId = layoutResId,
            bindingItemId = bindingItemId,
            viewModel = viewModel
        )

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
