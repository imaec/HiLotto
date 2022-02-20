package com.imaec.hilotto.ui

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseAdapter
import com.imaec.hilotto.base.BaseSingleViewAdapter
import com.imaec.hilotto.model.FitNumberDTO
import com.imaec.hilotto.ui.adapter.MyNumberAdapter
import com.imaec.hilotto.ui.adapter.WinHistoryAdapter
import java.text.DecimalFormat
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.roundToLong

@BindingAdapter(value = ["bindVisible", "bindVisibleMode"], requireAll = false)
fun View.bindVisible(isVisible: Boolean, visibleMode: Int = 0) {
    visibility = if (visibleMode == 0) {
        // VISIBLE, GONE
        if (isVisible) View.VISIBLE else View.GONE
    } else {
        // VISIBLE, INVISIBLE
        if (isVisible) View.VISIBLE else View.INVISIBLE
    }
}

@BindingAdapter("bindSingleClick")
fun View.bindSingleClick(clickListener: View.OnClickListener?) {
    clickListener?.also {
        setOnClickListener(OnSingleClickListener(it))
    } ?: setOnClickListener(null)
}

@BindingAdapter("backgroundNumberCircle")
fun View.setBackgroundNumberCircle(number: Int) {
    setBackgroundResource(
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

@BindingAdapter("bindBackgroundDot")
fun View.bindBackgroundDot(isBonus: Boolean) {
    setBackgroundResource(if (isBonus) R.drawable.dot_red else R.drawable.dot_primary)
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("bindItemList")
fun RecyclerView.bindItemList(itemList: List<Any>?) {
    (adapter as? BaseSingleViewAdapter<Any>)?.run {
        submitList(itemList)
    }
}

@BindingAdapter(value = ["bindNumber", "bindFitNumber"], requireAll = false)
fun TextView.bindFitNumber(number: Int, fitNumber: FitNumberDTO?) {
    text = number.toString()
    setTextColor(ContextCompat.getColor(context, R.color.white))
    setBackgroundNumberCircle(number)

    fitNumber ?: return
    fitNumber.listFitNumber.forEach {
        if (text.toString().toInt() == it) {
            setTextColor(ContextCompat.getColor(context, R.color.darkGray))
        }
    }
    if (text.toString().toInt() == fitNumber.numberBonus) {
        setTextColor(ContextCompat.getColor(context, R.color.darkGray))
    }
}

@BindingAdapter("bindRank")
fun TextView.bindRank(fitNumber: FitNumberDTO?) {
    fitNumber ?: return

    setBackgroundResource(if (fitNumber.rank > 0) R.drawable.bg_triangle else 0)
    text = if (fitNumber.rank > 0) "${fitNumber.rank}" else ""
}

@BindingAdapter("textByInt")
fun setTextByInt(textView: TextView, value: Int) {
    textView.text = value.toString()
}

@SuppressLint("SetTextI18n")

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

@BindingAdapter("rank")
fun setRank(textView: TextView, rank: Int) {
    textView.setBackgroundResource(if (rank > 0) R.drawable.bg_triangle else 0)
    textView.text = if (rank > 0) "$rank" else ""
}

@BindingAdapter(value = ["app:backgroundContinues", "app:isContinues"], requireAll = false)
fun setBackgroundContinues(
    textView: TextView,
    listContinues: List<String>,
    isContinues: Boolean = false
) {
    if (!isContinues) return

    try {
        val number = textView.text.toString().toInt()

        if (listContinues.contains("$number")) {
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
            textView.setTextColor(ContextCompat.getColor(textView.context, R.color.white))
        } else {
            textView.setBackgroundResource(0)
            textView.setTextColor(ContextCompat.getColor(textView.context, R.color.darkGray))
        }
    } catch (e: Exception) {
        textView.setBackgroundResource(0)
        textView.setTextColor(ContextCompat.getColor(textView.context, R.color.darkGray))
    }
}

@BindingAdapter("backgroundNumberCircle")
fun setBackgroundNumberCircle(textView: TextView, value: String) {
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
        textView.setTextColor(ContextCompat.getColor(textView.context, R.color.white))
    } catch (e: Exception) {
        textView.setBackgroundResource(0)
        textView.setTextColor(ContextCompat.getColor(textView.context, R.color.darkGray))
    }
}

@BindingAdapter("backgroundNumberSquare")
fun setBackgroundNumberSquare(textView: TextView, number: Int) {
    textView.setBackgroundResource(
        when (number) {
            in 1..10 -> R.drawable.bg_square_1
            in 11..20 -> R.drawable.bg_square_2
            in 21..30 -> R.drawable.bg_square_3
            in 31..40 -> R.drawable.bg_square_4
            in 41..45 -> R.drawable.bg_square_5
            else -> R.drawable.bg_square_1
        }
    )
    textView.setTextColor(ContextCompat.getColor(textView.context, R.color.white))
}

@BindingAdapter("backgroundRoundedLeftTop")
fun setBackgroundRoundedLeftTop(textView: TextView, number: Int) {
    textView.setBackgroundResource(
        when (number) {
            in 1..10 -> R.drawable.bg_left_top_rounded_1
            in 11..20 -> R.drawable.bg_left_top_rounded_2
            in 21..30 -> R.drawable.bg_left_top_rounded_3
            in 31..40 -> R.drawable.bg_left_top_rounded_4
            in 41..45 -> R.drawable.bg_left_top_rounded_5
            else -> R.drawable.bg_left_top_rounded_1
        }
    )
    textView.setTextColor(ContextCompat.getColor(textView.context, R.color.white))
}

@BindingAdapter("backgroundRoundedRightTop")
fun setBackgroundRoundedRightTop(textView: TextView, number: Int) {
    textView.setBackgroundResource(
        when (number) {
            in 1..10 -> R.drawable.bg_right_top_rounded_1
            in 11..20 -> R.drawable.bg_right_top_rounded_2
            in 21..30 -> R.drawable.bg_right_top_rounded_3
            in 31..40 -> R.drawable.bg_right_top_rounded_4
            in 41..45 -> R.drawable.bg_right_top_rounded_5
            else -> R.drawable.bg_right_top_rounded_1
        }
    )
    textView.setTextColor(ContextCompat.getColor(textView.context, R.color.white))
}

@BindingAdapter("adapter")
fun setAdapter(
    recyclerView: RecyclerView,
    adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
) {
    recyclerView.adapter = adapter
}

@BindingAdapter("items")
fun setItems(recyclerView: RecyclerView, items: List<Any>) {
    (recyclerView.adapter as BaseAdapter).apply {
        clearItem()
        addItems(items)
        notifyDataSetChanged()
    }
}

@BindingAdapter(value = ["app:items", "app:fit"], requireAll = true)
fun setItemsAndFit(
    recyclerView: RecyclerView,
    items: List<Any>,
    fitNumbers: List<FitNumberDTO>
) {
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

@BindingAdapter("adapter")
fun setAdapter(viewPager2: ViewPager2, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
    viewPager2.adapter = adapter
}

@BindingAdapter("items")
fun setItems(viewPager2: ViewPager2, items: List<Any>) {
    (viewPager2.adapter as BaseAdapter).apply {
        clearItem()
        addItems(items)
        notifyDataSetChanged()
    }
}

class OnSingleClickListener(
    private val clickListener: View.OnClickListener,
    private val intervalMs: Long = 1000
) : View.OnClickListener {
    private var canClick = AtomicBoolean(true)

    override fun onClick(v: View?) {
        if (canClick.getAndSet(false)) {
            v?.run {
                postDelayed(
                    {
                        canClick.set(true)
                    },
                    intervalMs
                )
                clickListener.onClick(v)
            }
        }
    }
}
