package com.imaec.hilotto.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imaec.hilotto.base.BaseAdapter
import com.imaec.hilotto.databinding.ItemStoreBinding
import com.imaec.hilotto.model.StoreDTO

class StoreAdapter : BaseAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemStoreBinding.inflate(LayoutInflater.from(parent.context))
        return ItemViewHolder(binding as ItemStoreBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.onBind(listItem[position] as StoreDTO)
        }
    }

    inner class ItemViewHolder(private val binding: ItemStoreBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: StoreDTO) {
            binding.apply {
                this.item = item
            }
        }
    }
}