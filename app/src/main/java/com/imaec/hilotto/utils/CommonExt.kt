package com.imaec.hilotto.utils

import android.content.res.Resources
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.android.material.bottomsheet.BottomSheetBehavior

val Int.px: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
val Float.dp: Float
    get() = (this * Resources.getSystem().displayMetrics.density)

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
