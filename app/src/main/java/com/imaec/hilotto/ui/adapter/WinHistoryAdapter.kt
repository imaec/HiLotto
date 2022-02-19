package com.imaec.hilotto.ui.adapter

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
    }

    inner class ItemViewHolder(private val binding: ItemWinHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: NumberEntity, fitNumberDTO: FitNumberDTO) {
            fitNumberDTO.apply {
                listFitNumber.forEach {
                    if (item.number1 == it) isFitNo1 = true
                    if (item.number2 == it) isFitNo2 = true
                    if (item.number3 == it) isFitNo3 = true
                    if (item.number4 == it) isFitNo4 = true
                    if (item.number5 == it) isFitNo5 = true
                    if (item.number6 == it) isFitNo6 = true
                }
                arrayOf(
                    item.number1,
                    item.number2,
                    item.number3,
                    item.number4,
                    item.number5,
                    item.number6
                ).forEachIndexed { index, number ->
                    listIsFitBonus.add(numberBonus == number)
                }
            }

            binding.apply {
                this.item = item
                this.fitNumberDTO = fitNumberDTO
                this.rank = fitNumberDTO.rank
            }
        }
    }
}
