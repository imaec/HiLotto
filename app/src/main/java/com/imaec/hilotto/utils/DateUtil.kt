package com.imaec.hilotto.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class DateUtil {

    companion object {
        @SuppressLint("SimpleDateFormat")
        fun getDate(format: String = "yyyyMMdd") : String {
            return SimpleDateFormat(format).format(Date())
        }

        @SuppressLint("SimpleDateFormat")
        fun getDateChangeFormat(date: String, fromFormat: String = "yyyyMMdd", toFormat: String = "yyyyMMdd") : String {
            val fromSdf = SimpleDateFormat(fromFormat)
            val toSdf = SimpleDateFormat(toFormat)
            return toSdf.format(fromSdf.parse(date) ?: Date())
        }
    }
}