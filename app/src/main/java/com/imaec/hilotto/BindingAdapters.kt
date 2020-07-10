package com.imaec.hilotto

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.imaec.hilotto.base.BaseAdapter
import java.text.DecimalFormat
import kotlin.math.roundToLong

object BindingAdapters {

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter("decimalAndUnit")
    fun setDecimal(textView: TextView, value: Long) {
        val decimal = DecimalFormat("###,###").format(value)
        if (value > 100000000) {
            val unitValue = (value / 100000000.0).roundToLong()
            textView.text = "$decimal (약 ${unitValue}억)"
        } else {
            textView.text = decimal
        }
    }

    @JvmStatic
    @BindingAdapter("adapter")
    fun setAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        recyclerView.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("items")
    fun setItems(recyclerView: RecyclerView, items: ArrayList<Any>) {
        (recyclerView.adapter as BaseAdapter).apply {
            clearItem()
            addItems(items)
            notifyDataSetChanged()
        }
    }
}