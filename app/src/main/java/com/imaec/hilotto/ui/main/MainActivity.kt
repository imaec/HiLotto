package com.imaec.hilotto.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseActivity
import com.imaec.hilotto.databinding.ActivityMainBinding
import com.imaec.hilotto.ui.splash.SplashActivity
import com.imaec.hilotto.utils.SharedPreferenceUtil
import com.imaec.hilotto.utils.setStatusBarColor
import com.imaec.hilotto.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    companion object {
        val isLoaded = MutableLiveData(false)
        val progress = MutableLiveData(0)
    }

    private val viewModel by viewModels<MainViewModel>()
    private val lottoViewModel by viewModels<LottoViewModel>()

    private var loadedCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        setStatusBarColor(R.color.white, true)
        super.onCreate(savedInstanceState)

        startActivityForResult(Intent(this, SplashActivity::class.java), 0)

        setupBinding()
        setupLayout()
        setupAd()
        setupData()
        setupObserver()
    }

    override fun onBackPressed() {
        with(binding) {
            if (vpMain.currentItem == 0) {
                super.onBackPressed()
            } else {
                vpMain.setCurrentItem(0, false)
                bnvMain.selectedItemId = R.id.nav_home
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            interstitialAd.show()
            isLoaded.value = false
        }
    }

    private fun setupBinding() {
        with(binding) {
            vm = viewModel
        }
    }

    private fun setupLayout() {
        with(binding.vpMain) {
            adapter = MainPagerAdapter(this@MainActivity)
            isUserInputEnabled = false
        }
        with(binding.bnvMain) {
            setOnItemSelectedListener {
                if (it.itemId == R.id.nav_home) {
                    setStatusBarColor(R.color.white, true)
                } else {
                    setStatusBarColor(R.color.colorPrimary, false)
                }
                binding.vpMain.setCurrentItem(
                    when (it.itemId) {
                        R.id.nav_home -> 0
                        R.id.nav_statistics -> 1
                        R.id.nav_recommend -> 2
                        R.id.nav_my -> 3
                        else -> 4
                    },
                    false
                )
                true
            }
        }
    }

    private fun setupAd() {
        showAd(
            adId = R.string.ad_id_main_front,
            isRandom = false,
            onLoaded = {
                loadedCount++
                if (loadedCount == 2) isLoaded.value = true
            },
            onClosed = {}
        )
    }

    private fun setupData() {
        showProgress()
        val curDrwNo = SharedPreferenceUtil.getInt(
            context = this,
            key = SharedPreferenceUtil.KEY.PREF_CUR_DRW_NO,
            def = 1
        )
        with(lottoViewModel) {
            getLotto(
                curDrwNo = curDrwNo,
                callback = { isSuccess ->
                    hideProgress()
                    if (isSuccess) {
                        loadedCount++
                        if (loadedCount == 2) isLoaded.value = true
                    } else {
                        toast(R.string.msg_data_fail)
                    }
                },
                callbackProgress = { // progress
                    progress.value = it
                }
            )
            getStore(curDrwNo)
        }
    }

    private fun setupObserver() {
        viewModel.settingChanged.observe(this) {
            if (it) {
                setupData()
                viewModel.init()
            }
        }
        lottoViewModel.curDrwNo.observe(this) {
            SharedPreferenceUtil.putValue(
                context = this@MainActivity,
                key = SharedPreferenceUtil.KEY.PREF_CUR_DRW_NO,
                value = it
            )
        }
    }
}
