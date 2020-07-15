package com.imaec.hilotto.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imaec.hilotto.base.BaseAdapter
import com.imaec.hilotto.databinding.ItemContinuesBinding
import com.imaec.hilotto.databinding.ItemNumberBinding
import com.imaec.hilotto.model.ContinueDTO

class ContinuesSubAdapter : BaseAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemNumberBinding.inflate(LayoutInflater.from(parent.context))
        return ItemViewHolder(binding as ItemNumberBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.onBind(listItem[position] as Int)
        }
    }

    inner class ItemViewHolder(val binding: ItemNumberBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Int) {
            binding.apply {
                this.item = item
            }
        }
    }
}