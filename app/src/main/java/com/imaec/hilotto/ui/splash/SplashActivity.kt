package com.imaec.hilotto.ui.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseActivity
import com.imaec.hilotto.databinding.ActivitySplashBinding
import com.imaec.hilotto.ui.main.MainActivity
import com.imaec.hilotto.utils.setStatusBarColor
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("SetTextI18n", "CustomSplashScreen")
@Suppress("REDUNDANT_LABEL_WARNING")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        setStatusBarColor(R.color.white, true)
        super.onCreate(savedInstanceState)

        setupBinding()
        setupLoadingObserver(viewModel)
        setupObserver()
    }

    override fun onBackPressed() {
    }

    private fun setupBinding() {
        with(binding) {
            vm = viewModel
        }
    }

    private fun setupObserver() {
        MainActivity.progress.observe(this) {
            viewModel.progress.value = it
            viewModel.loadingText.value = if (it in 1 until 99) {
                "데이터를 가져오는 중입니다..($it%)"
            } else {
                "잠시만 기다려 주세요."
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
