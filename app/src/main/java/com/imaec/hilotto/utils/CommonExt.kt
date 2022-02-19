package com.imaec.hilotto.utils

import android.content.res.Resources

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
val Float.px: Float
    get() = (this * Resources.getSystem().displayMetrics.density)
