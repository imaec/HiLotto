package com.imaec.hilotto

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.imaec.hilotto.base.BaseAdapter
import com.imaec.hilotto.ui.adapter.MyNumberAdapter
import java.text.DecimalFormat
import kotlin.math.roundToLong

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("textByInt")
    fun setTextByInt(textView: TextView, value: Int) {
        textView.text = value.toString()
    }

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

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter("decimalAndUnitPerOne")
    fun setDecimalPerOne(textView: TextView, value: Long) {
        val decimal = DecimalFormat("###,###").format(value)
        if (value > 100000000) {
            val unitValue = (value / 100000000.0).roundToLong()
            textView.text = "1인당 약 ${unitValue}억 원"
        } else {
            textView.text = "1인당 약 $decimal 원"
        }
    }

    @JvmStatic
    @BindingAdapter("backgroundNumber")
    fun setBackgroundNumber(textView: TextView, value: String) {
        textView.apply {
            if (value != ":") {
                setBackgroundResource(R.drawable.bg_circle_accent)
                setTextColor(textView.context.resources.getColor(R.color.white))
            } else {
                setBackgroundResource(0)
                setTextColor(textView.context.resources.getColor(R.color.darkGray))
            }
        }
    }

    @JvmStatic
    @BindingAdapter("textCreateToggle")
    fun setTextCreateToggle(textView: TextView, listInclude: List<String>) {
        var str = textView.context.getString(R.string.remove_numbers)
        listInclude.forEach {
            if (it.isEmpty()) {
                str = textView.context.getString(R.string.create_numbers)
                return@forEach
            }
        }
        textView.text = str
    }

    @JvmStatic
    @BindingAdapter("fitNumber")
    fun setFitNumber(textView: TextView, listFit: List<Int>) {
        listFit.forEach {
            if (textView.text.toString().toInt() == it) {
                textView.setTextColor(textView.context.resources.getColor(R.color.darkGray))
            }
        }
    }

    @JvmStatic
    @BindingAdapter("adapter")
    fun setAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        recyclerView.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("items")
    fun setItems(recyclerView: RecyclerView, items: List<Any>) {
        (recyclerView.adapter as BaseAdapter).apply {
            clearItem()
            addItems(items)
            notifyDataSetChanged()
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["app:items", "app:fit"], requireAll = true)
    fun setItemsAndFit(recyclerView: RecyclerView, items: List<Any>, fitNumbers: List<List<Int>>) {
        (recyclerView.adapter as MyNumberAdapter).apply {
            clearItem()
            addItems(items)
            setFitNumbers(fitNumbers)
            notifyDataSetChanged()
        }
    }

    @JvmStatic
    @BindingAdapter("isVisible")
    fun isVisible(view: View, isVisible: Boolean) {
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}