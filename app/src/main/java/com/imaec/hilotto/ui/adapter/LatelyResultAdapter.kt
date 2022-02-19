package com.imaec.hilotto.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imaec.hilotto.base.BaseAdapter
import com.imaec.hilotto.databinding.ItemLatelyResultBinding
import com.imaec.hilotto.model.LottoDTO

class LatelyResultAdapter : BaseAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemLatelyResultBinding.inflate(LayoutInflater.from(parent.context))
        return ItemViewHolder(binding as ItemLatelyResultBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.onBind(listItem[position] as LottoDTO)
        }
    }

    inner class ItemViewHolder(private val binding: ItemLatelyResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: LottoDTO) {
            binding.apply {
                this.item = item
                root.setOnClickListener {
                    onClick(item)
                }
            }
        }
    }
}
