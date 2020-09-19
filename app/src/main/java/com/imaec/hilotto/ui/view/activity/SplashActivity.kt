package com.imaec.hilotto.ui.view.activity

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseActivity
import com.imaec.hilotto.databinding.ActivitySplashBinding

@Suppress("REDUNDANT_LABEL_WARNING")
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MainActivity.isLoaded.observe(this, Observer {
            if (it) {
                setResult(RESULT_OK)
                finish()
            }
        })
    }

    override fun onBackPressed() {
    }
}