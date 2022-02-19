package com.imaec.hilotto.ui.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseActivity
import com.imaec.hilotto.databinding.ActivitySplashBinding

@SuppressLint("SetTextI18n")
@Suppress("REDUNDANT_LABEL_WARNING")
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MainActivity.progress.observe(
            this,
            Observer {
                binding.apply {
                    progressHorizontal.progress = it
                    textLoading.text = "데이터를 가져오는 중입니다..($it%)"
                    if (it >= 100) {
                        progressCircular.visibility = View.VISIBLE
                        textLoading.text = "잠시만 기다려 주세요."
                    }
                }
                Log.d(TAG, "    ## progress : $it%")
            }
        )
        MainActivity.isLoaded.observe(
            this,
            Observer {
                if (it) {
                    setResult(RESULT_OK)
                    finish()
                }
            }
        )
    }

    override fun onBackPressed() {
    }
}
