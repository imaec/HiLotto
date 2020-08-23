package com.imaec.hilotto.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imaec.hilotto.base.BaseAdapter
import com.imaec.hilotto.databinding.ItemMyNumberBinding
import com.imaec.hilotto.model.FitNumberDTO
import com.imaec.hilotto.room.entity.NumberEntity

class MyNumberAdapter : BaseAdapter() {

    private var listFit = emptyList<FitNumberDTO>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemMyNumberBinding.inflate(LayoutInflater.from(parent.context))
        return ItemViewHolder(binding as ItemMyNumberBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.onBind(listItem[position] as NumberEntity, listFit[position])
        }
    }

    fun setFitNumbers(listFit: List<FitNumberDTO>) {
        this.listFit = listFit
        listFit.forEach {
            Log.d(TAG, "    ## $it")
        }
    }

    inner class ItemViewHolder(val binding: ItemMyNumberBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: NumberEntity, fitNumberDTO: FitNumberDTO) {
            binding.apply {
                this.item = item
                this.fitNumberDTO = fitNumberDTO
                this.rank = when (fitNumberDTO.listFitNumber.size) {
                    6 -> 1
                    5 -> if (fitNumberDTO.numberBonus > 0) 2 else 3
                    4 -> 4
                    3 -> 5
                    else -> 0
                }
                root.setOnClickListener { onClick(item) }
                root.setOnLongClickListener {
                    onLongClick(item)
                    true
                }
            }
        }
    }
}