package com.imaec.hilotto.ui.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseActivity
import com.imaec.hilotto.databinding.ActivityMainBinding
import com.imaec.hilotto.repository.FirebaseRepository
import com.imaec.hilotto.repository.LottoRepository
import com.imaec.hilotto.ui.view.fragment.*
import com.imaec.hilotto.utils.SharedPreferenceUtil
import com.imaec.hilotto.viewmodel.LottoViewModel
import com.imaec.hilotto.viewmodel.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main), BottomNavigationView.OnNavigationItemSelectedListener {

    companion object {
        val isLoaded = MutableLiveData<Boolean>(false)
        val progress = MutableLiveData<Int>(0)
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var sharedViewModel: LottoViewModel
    private lateinit var activeFragment: Fragment

    private val lottoRepository = LottoRepository()
    private val firebaseRepository = FirebaseRepository()
    private val fragmentHome = HomeFragment()
    private val fragmentStatistics = StatisticsFragment()
    private val fragmentRecommend = RecommendFragment()
    private val fragmentMy = MyFragment()
    private val fragmentSetting = SettingFragment()

    private var loadedCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivityForResult(Intent(this, SplashActivity::class.java), 0)

        mainViewModel = getViewModel(MainViewModel::class.java)
        sharedViewModel = getViewModel(LottoViewModel::class.java, lottoRepository, firebaseRepository)

        binding.apply {
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.mainViewModel
            bottomNavigation.setOnNavigationItemSelectedListener(this@MainActivity)
        }
        showAd(R.string.ad_id_main_front, false, {
            loadedCount++
            if (loadedCount == 2) isLoaded.value = true
        }, {
            init()
        })

        showProgress()
        val curDrwNo = SharedPreferenceUtil.getInt(this, SharedPreferenceUtil.KEY.PREF_CUR_DRW_NO, 1)
        sharedViewModel.apply {
            getLotto(curDrwNo, { isSuccess ->
                hideProgress()
                if (isSuccess) {
                    loadedCount++
                    if (loadedCount == 2) isLoaded.value = true
                } else {
                    Toast.makeText(this@MainActivity, R.string.msg_data_fail, Toast.LENGTH_SHORT).show()
                }
            }, { // progress
                progress.value = it
            })
            getStore()
            this.curDrwNo.observe(this@MainActivity, Observer {
                SharedPreferenceUtil.putValue(this@MainActivity, SharedPreferenceUtil.KEY.PREF_CUR_DRW_NO, it)
            })
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        setFragment(when (item.itemId) {
            R.id.navigation_home -> fragmentHome
            R.id.navigation_statistics -> fragmentStatistics
            R.id.navigation_recommend -> fragmentRecommend
            R.id.navigation_my-> fragmentMy
            R.id.navigation_setting -> fragmentSetting
            else -> fragmentHome
        })
        return true
    }

    override fun onBackPressed() {
        when {
            binding.bottomNavigation.selectedItemId != R.id.navigation_home -> {
                binding.bottomNavigation.selectedItemId = R.id.navigation_home
            }
            else -> super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            interstitialAd.show()
            isLoaded.value = false
        }
    }

    fun onClick(view: View) {
        Log.d(TAG, "    ## onClick($view)")
        when (activeFragment) {
            is HomeFragment -> {
                fragmentHome.onClick(view)
            }
            is RecommendFragment -> {
                fragmentRecommend.onClick(view)
            }
            is MyFragment -> {
                fragmentMy.onClick(view)
            }
            is SettingFragment -> {
                fragmentSetting.onClick(view)
            }
            else -> {
                
            }
        }
    }

    private fun init() {
        supportFragmentManager.beginTransaction().add(R.id.frame, fragmentSetting, getString(R.string.setting)).hide(fragmentSetting).commitAllowingStateLoss()
        supportFragmentManager.beginTransaction().add(R.id.frame, fragmentMy, getString(R.string.my)).hide(fragmentMy).commitAllowingStateLoss()
        supportFragmentManager.beginTransaction().add(R.id.frame, fragmentRecommend, getString(R.string.recommend)).hide(fragmentRecommend).commitAllowingStateLoss()
        supportFragmentManager.beginTransaction().add(R.id.frame, fragmentStatistics, getString(R.string.statistics)).hide(fragmentStatistics).commitAllowingStateLoss()
        supportFragmentManager.beginTransaction().add(R.id.frame, fragmentHome, getString(R.string.home)).commitAllowingStateLoss()
        activeFragment = fragmentHome
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .hide(activeFragment)
            .show(fragment)
            .commit()
        activeFragment = fragment
    }
}
