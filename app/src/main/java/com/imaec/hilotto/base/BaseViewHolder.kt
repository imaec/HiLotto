package com.imaec.hilotto.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView

class BaseViewHolder(
    parent: ViewGroup,
    @LayoutRes private val layoutResourceId: Int,
    private val bindingItemId: Int,
    private val viewModel: Map<Int, ViewModel>
) : RecyclerView.ViewHolder(
    LayoutInflater
        .from(parent.context)
        .inflate(layoutResourceId, parent, false)
) {
    private val binding: ViewDataBinding = DataBindingUtil.bind(itemView)!!

    fun bind(item: Any) {
        binding.setVariable(bindingItemId, item)

        for (key in viewModel.keys) {
            binding.setVariable(key, viewModel[key])
        }
        binding.executePendingBindings()
    }
}
