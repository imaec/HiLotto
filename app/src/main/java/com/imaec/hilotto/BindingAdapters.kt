package com.imaec.hilotto

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.imaec.hilotto.base.BaseAdapter
import com.imaec.hilotto.model.FitNumberDTO
import com.imaec.hilotto.ui.adapter.MyNumberAdapter
import com.imaec.hilotto.ui.adapter.WinHistoryAdapter
import java.lang.Exception
import java.text.DecimalFormat
import kotlin.math.roundToLong

object BindingAdapters {

    private val TAG = this::class.java.simpleName

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
    fun setFitNumber(textView: TextView, fitNumberDTO: FitNumberDTO) {
        textView.setTextColor(textView.context.resources.getColor(R.color.white))
        fitNumberDTO.listFitNumber.forEach {
            if (textView.text.toString().toInt() == it) {
                textView.setTextColor(textView.context.resources.getColor(R.color.darkGray))
            }
        }
        if (textView.text.toString().toInt() == fitNumberDTO.numberBonus) {
            textView.setTextColor(textView.context.resources.getColor(R.color.gray))
        }
    }

    @JvmStatic
    @BindingAdapter("rank")
    fun setRank(textView: TextView, fitNumberDTO: FitNumberDTO) {
        textView.setBackgroundResource(if (fitNumberDTO.rank > 0) R.drawable.bg_triangle else 0)
        textView.text = if (fitNumberDTO.rank > 0) "${fitNumberDTO.rank}" else ""
    }

    @JvmStatic
    @BindingAdapter("rank")
    fun setRank(textView: TextView, rank: Int) {
        textView.setBackgroundResource(if (rank > 0) R.drawable.bg_triangle else 0)
        textView.text = if (rank > 0) "$rank" else ""
    }

    @JvmStatic
    @BindingAdapter("backgroundNumber")
    fun setBackgroundNumber(textView: TextView, value: String) {
        try {
            val number = value.toInt()
            textView.setBackgroundResource(
                when (number) {
                    in 1..10 -> R.drawable.bg_circle_1
                    in 11..20 -> R.drawable.bg_circle_2
                    in 21..30 -> R.drawable.bg_circle_3
                    in 31..40 -> R.drawable.bg_circle_4
                    in 41..45 -> R.drawable.bg_circle_5
                    else -> R.drawable.bg_circle_1
                }
            )
            textView.setTextColor(textView.context.resources.getColor(R.color.white))
        } catch (e: Exception) {
            textView.setBackgroundResource(0)
            textView.setTextColor(textView.context.resources.getColor(R.color.darkGray))
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
    fun setItemsAndFit(recyclerView: RecyclerView, items: List<Any>, fitNumbers: List<FitNumberDTO>) {
        val adapter = recyclerView.adapter
        if (adapter is MyNumberAdapter) {
            adapter.apply {
                clearItem()
                addItems(items)
                setFitNumbers(fitNumbers)
                notifyDataSetChanged()
            }
        } else if (adapter is WinHistoryAdapter) {
            adapter.apply {
                clearItem()
                addItems(items)
                setFitNumbers(fitNumbers)
                notifyDataSetChanged()
            }
        }
    }

    @JvmStatic
    @BindingAdapter("isVisible")
    fun isVisible(view: View, isVisible: Boolean) {
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("backgroundNumber")
    fun setBackgroundNumber(view: View, number: Int) {
        view.setBackgroundResource(
            when (number) {
                in 1..10 -> R.drawable.bg_circle_1
                in 11..20 -> R.drawable.bg_circle_2
                in 21..30 -> R.drawable.bg_circle_3
                in 31..40 -> R.drawable.bg_circle_4
                in 41..45 -> R.drawable.bg_circle_5
                else -> R.drawable.bg_circle_1
            }
        )
    }
}