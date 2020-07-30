package com.imaec.hilotto.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imaec.hilotto.base.BaseAdapter
import com.imaec.hilotto.databinding.ItemContinuesBinding
import com.imaec.hilotto.model.ContinueDTO
import com.imaec.hilotto.ui.util.NumberDecoration

class ContinuesAdapter : BaseAdapter() {

    private val itemDecoration: NumberDecoration by lazy { NumberDecoration(binding.root.context) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemContinuesBinding.inflate(LayoutInflater.from(parent.context))
        return ItemViewHolder(binding as ItemContinuesBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.onBind(listItem[position] as ContinueDTO)
        }
    }

    inner class ItemViewHolder(val binding: ItemContinuesBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: ContinueDTO) {
            binding.apply {
                recyclerItemNum.adapter = NumberAdapter()
                recyclerItemNum.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
                recyclerItemNum.removeItemDecoration(itemDecoration)
                recyclerItemNum.addItemDecoration(itemDecoration)
                this.item = item
            }
        }
    }
}