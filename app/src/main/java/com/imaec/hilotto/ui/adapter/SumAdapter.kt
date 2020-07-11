package com.imaec.hilotto.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imaec.hilotto.base.BaseAdapter
import com.imaec.hilotto.databinding.ItemSumBinding
import com.imaec.hilotto.model.SumDTO

class SumAdapter : BaseAdapter() {

    private var sumMax = 255

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemSumBinding.inflate(LayoutInflater.from(parent.context))
        return ItemViewHolder(binding as ItemSumBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.onBind(listItem[position] as SumDTO)
        }
    }

    fun setSumMax(sumMax: Int) {
        this.sumMax = sumMax
    }

    inner class ItemViewHolder(val binding: ItemSumBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: SumDTO) {
            item.sumMax = sumMax
            binding.apply {
                this.item = item
            }
        }
    }
}