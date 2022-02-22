package com.imaec.hilotto.ui

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseSingleViewAdapter
import com.imaec.hilotto.model.FitNumberDTO
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

@BindingAdapter(value = ["bindNumber", "bindFitNumber"], requireAll = false)
fun TextView.bindFitNumber(number: Int, fitNumber: FitNumberDTO?) {
    text = number.toString()
    setTextColor(ContextCompat.getColor(context, R.color.white))
    setBackgroundNumberCircle(number)

    fitNumber ?: return
    fitNumber.listFitNumber.forEach {
        if (text.toString().toInt() == it) {
            setShadowLayer(1.5f, 3f, 2f, ContextCompat.getColor(context, R.color.gray))
        }
    }
    if (text.toString().toInt() == fitNumber.numberBonus) {
        setShadowLayer(1.5f, 3f, 2f, ContextCompat.getColor(context, R.color.gray))
    }
}

@BindingAdapter("bindRank")
fun TextView.bindRank(fitNumber: FitNumberDTO?) {
    fitNumber ?: return

    setBackgroundResource(if (fitNumber.rank > 0) R.drawable.bg_triangle else 0)
    text = if (fitNumber.rank > 0) "${fitNumber.rank}" else ""
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("bindItemList")
fun RecyclerView.bindItemList(itemList: List<Any>?) {
    (adapter as? BaseSingleViewAdapter<Any>)?.run {
        submitList(itemList)
    }
}

@BindingAdapter(value = ["bindProgress", "bindMax"])
fun ProgressBar.bindSumProgress(sumProgress: Int, sumMax: Int) {
    max = sumMax

    with(ValueAnimator.ofInt(0, sumProgress)) {
        interpolator =
            AnimationUtils.loadInterpolator(context, android.R.anim.decelerate_interpolator)
        startDelay = 0
        duration = 500
        addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            progress = value
        }
        start()
    }
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
