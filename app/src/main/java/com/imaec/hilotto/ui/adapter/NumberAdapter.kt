package com.imaec.hilotto.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imaec.hilotto.base.BaseAdapter
import com.imaec.hilotto.databinding.ItemNumberBinding

class NumberAdapter(
    private val isContinues: Boolean = false
) : BaseAdapter() {

    private val listContinues = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemNumberBinding.inflate(LayoutInflater.from(parent.context))
        return ItemViewHolder(binding as ItemNumberBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.onBind(listItem[position] as String)
        }
    }

    fun setListContinues(listContinues: List<String>) {
        this.listContinues.addAll(listContinues)
    }

    inner class ItemViewHolder(val binding: ItemNumberBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: String) {
            binding.apply {
                this.item = item
                this.listContinues = this@NumberAdapter.listContinues
                this.isContinues = this@NumberAdapter.isContinues
                root.setOnClickListener {
                    onClick(item)
                }
            }
        }
    }
}