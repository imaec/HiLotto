package com.imaec.hilotto.utils

import android.content.Context
import android.widget.Toast

fun Context.toast(msg: CharSequence) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.toast(strRes: Int) {
    Toast.makeText(this, strRes, Toast.LENGTH_SHORT).show()
}
