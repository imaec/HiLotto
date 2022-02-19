package com.imaec.hilotto.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment

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
