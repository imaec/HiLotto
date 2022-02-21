package com.imaec.hilotto.utils

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.imaec.hilotto.ui.view.dialog.InfoDialog

inline fun <reified T : Activity> Fragment.startActivity(
    bundle: Bundle? = null,
    transitionBundle: Bundle? = null
) {
    val intent = Intent(context, T::class.java)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    startActivity(intent, transitionBundle)
}

inline fun <reified T : Activity> Fragment.startActivityWithFinish(bundle: Bundle? = null) {
    startActivity<T>(bundle)
    activity?.finish()
}

fun Fragment.showInfoDialog(msg: String) {
    InfoDialog(requireContext(), msg).show()
}

fun Fragment.getVersion(): String {
    var version = "Unknown"
    val packageInfo: PackageInfo

    try {
        packageInfo = requireContext().applicationContext
            .packageManager
            .getPackageInfo(requireContext().applicationContext.packageName, 0)
        version = packageInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        return version
    }
    return version
}
