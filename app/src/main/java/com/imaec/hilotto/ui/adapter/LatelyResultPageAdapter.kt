package com.imaec.hilotto.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imaec.hilotto.base.BaseAdapter
import com.imaec.hilotto.databinding.PageLatelyResultBinding
import com.imaec.hilotto.model.LottoDTO

class LatelyResultPageAdapter : BaseAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = PageLatelyResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PageViewHolder(binding as PageLatelyResultBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PageViewHolder) {
            holder.onBind(listItem[position] as LottoDTO)
        }
    }

    inner class PageViewHolder(private val binding: PageLatelyResultBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: LottoDTO) {
            binding.apply {
                this.item = item
            }
        }
    }
}