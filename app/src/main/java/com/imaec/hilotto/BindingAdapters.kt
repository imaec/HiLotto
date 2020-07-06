package com.imaec.hilotto

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
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
}