package com.imaec.hilotto.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imaec.hilotto.base.BaseAdapter
import com.imaec.hilotto.databinding.ItemWinHistoryBinding
import com.imaec.hilotto.model.FitNumberDTO
import com.imaec.hilotto.room.entity.NumberEntity

class WinHistoryAdapter : BaseAdapter() {

    private var listFit = emptyList<FitNumberDTO>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemWinHistoryBinding.inflate(LayoutInflater.from(parent.context))
        return ItemViewHolder(binding as ItemWinHistoryBinding)
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

    inner class ItemViewHolder(private val binding: ItemWinHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: NumberEntity, fitNumberDTO: FitNumberDTO) {
            binding.apply {
                this.item = item
                this.fitNumberDTO = fitNumberDTO
                this.rank = fitNumberDTO.rank
            }
        }
    }
}