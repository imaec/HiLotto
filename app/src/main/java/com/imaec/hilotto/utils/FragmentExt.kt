package com.imaec.hilotto.utils

import android.app.Activity
import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import com.imaec.hilotto.ui.view.dialog.InfoDialog

inline fun <reified T : Activity> Fragment.startActivity(
    bundle: Bundle? = null,
    transitionBundle: Bundle? = null
) {
    requireActivity().startActivity<T>(bundle, transitionBundle)
}

inline fun <reified T : Activity> Fragment.startActivityWithFinish(bundle: Bundle? = null) {
    requireActivity().startActivityWithFinish<T>(bundle)
}

fun Fragment.toast(msg: CharSequence) {
    requireContext().toast(msg)
}

fun Fragment.toast(strRes: Int) {
    requireContext().toast(strRes)
}

fun Fragment.showInfoDialog(msg: String) {
    InfoDialog(requireContext(), msg).show()
}

fun Fragment.getVersion(): String = requireActivity().getVersion()

fun Fragment.setStatusBarColor(@ColorRes color: Int, isLight: Boolean) {
    requireActivity().setStatusBarColor(color, isLight)
}
