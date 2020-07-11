package com.imaec.hilotto.utils

import android.content.Context

class Utils {

    companion object {
        fun dp(context: Context, dpValue: Int): Int {
            val d = context.resources.displayMetrics.density
            return (dpValue * d).toInt()
        }
    }
}