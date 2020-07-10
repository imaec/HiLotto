package com.imaec.hilotto.base

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected val TAG = this::class.java.simpleName

    protected lateinit var binding: ViewDataBinding
    protected val listItem = ArrayList<Any>()
    protected lateinit var onClick: (Any) -> Unit
    protected lateinit var onLongClick: (Any) -> Unit

    override fun getItemCount(): Int = listItem.size

    fun addItems(list: List<Any>) {
        listItem.addAll(list)
    }

    fun clearItem() {
        listItem.clear()
        notifyDataSetChanged()
    }

    fun addOnClickListener(onClick: (Any) -> Unit) {
        this.onClick = onClick
    }

    fun addOnLongClickListener(onLongClick: (Any) -> Unit) {
        this.onLongClick = onLongClick
    }
}