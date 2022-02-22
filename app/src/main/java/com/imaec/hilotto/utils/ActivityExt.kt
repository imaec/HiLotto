package com.imaec.hilotto.utils

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat

inline fun <reified T : Activity> Activity.startActivity(
    bundle: Bundle? = null,
    transitionBundle: Bundle? = null
) {
    val intent = Intent(this, T::class.java)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    startActivity(intent, transitionBundle)
}

inline fun <reified T : Activity> Activity.startActivityWithFinish(bundle: Bundle? = null) {
    startActivity<T>(bundle)
    finish()
}

fun Activity.getVersion(): String {
    var version = "Unknown"
    val packageInfo: PackageInfo

    try {
        packageInfo = applicationContext
            .packageManager
            .getPackageInfo(applicationContext.packageName, 0)
        version = packageInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        return version
    }
    return version
}

fun Activity.setStatusBarColor(@ColorRes color: Int, isLight: Boolean) {
    with(window) {
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        statusBarColor = ContextCompat.getColor(context, color)
        WindowInsetsControllerCompat(this, decorView).isAppearanceLightStatusBars = isLight
    }
}
