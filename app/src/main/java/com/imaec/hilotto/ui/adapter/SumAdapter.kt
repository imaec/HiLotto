package com.imaec.hilotto.ui.adapter

import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseAdapter
import com.imaec.hilotto.databinding.ItemSumBinding
import com.imaec.hilotto.model.SumDTO
import kotlinx.android.synthetic.main.item_sum.view.*

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

    @Suppress("UNCHECKED_CAST")
    inner class ItemViewHolder(val binding: ItemSumBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: SumDTO) {
            item.sumMax = sumMax

            val listSortedTemp = (listItem as ArrayList<SumDTO>).sortedWith(compareBy { it.sum })
            binding.apply {
                this.item = item
                progressSum.progressDrawable.setColorFilter(
                    when (item.sum) {
                        listSortedTemp[0].sum -> ContextCompat.getColor(
                            itemView.context,
                            android.R.color.holo_blue_dark
                        ) // 최소
                        listSortedTemp[itemCount - 1].sum -> ContextCompat.getColor(
                            itemView.context,
                            android.R.color.holo_red_dark
                        ) // 최대
                        else -> ContextCompat.getColor(itemView.context, android.R.color.black)
                    },
                    PorterDuff.Mode.SRC_IN
                )
            }
        }
    }
}
