package com.imaec.hilotto.utils

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.android.material.bottomsheet.BottomSheetBehavior

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
val Float.px: Float
    get() = (this * Resources.getSystem().displayMetrics.density)

fun Context.toast(msg: CharSequence) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.toast(strRes: Int) {
    Toast.makeText(this, strRes, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(msg: CharSequence) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(strRes: Int) {
    Toast.makeText(requireContext(), strRes, Toast.LENGTH_SHORT).show()
}

fun <T> MediatorLiveData<T>.addSourceList(vararg liveDatas: LiveData<*>, onChanged: () -> T) {
    liveDatas.forEach {
        this.addSource(it) { value = onChanged() }
    }
}

fun <V : View> BottomSheetBehavior<V>.expand(): BottomSheetBehavior<V> {
    state = BottomSheetBehavior.STATE_EXPANDED
    return this
}

fun <V : View> BottomSheetBehavior<V>.hide(): BottomSheetBehavior<V> {
    state = BottomSheetBehavior.STATE_HIDDEN
    return this
}
