package com.imaec.hilotto.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

class Utils {

    companion object {
        fun dp(context: Context, dpValue: Int): Int {
            val d = context.resources.displayMetrics.density
            return (dpValue * d).toInt()
        }

        fun getVersion(context: Context) : String {
            var version = "Unknown"
            val packageInfo: PackageInfo

            try {
                packageInfo = context.applicationContext
                    .packageManager
                    .getPackageInfo(context.applicationContext.packageName, 0)
                version = packageInfo.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                return version
            }
            return version
        }
    }
}