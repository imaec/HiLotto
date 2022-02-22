package com.imaec.hilotto.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseActivity
import com.imaec.hilotto.databinding.ActivitySplashBinding
import com.imaec.hilotto.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("SetTextI18n")
@Suppress("REDUNDANT_LABEL_WARNING")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupObserver()
    }

    override fun onBackPressed() {
    }

    private fun setupObserver() {
        MainActivity.progress.observe(this) {
            binding.apply {
                pbHorizontal.progress = it
                tvLoading.text = "데이터를 가져오는 중입니다..($it%)"
                if (it >= 100) {
                    pbCircular.visibility = View.VISIBLE
                    tvLoading.text = "잠시만 기다려 주세요."
                }
            }
        }
        MainActivity.isLoaded.observe(this) {
            if (it) {
                setResult(RESULT_OK)
                finish()
            }
        }
    }
}