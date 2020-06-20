package com.imaec.hilotto.ui.view.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseActivity
import com.imaec.hilotto.databinding.ActivityMainBinding
import com.imaec.hilotto.ui.view.fragment.HomeFragment
import com.imaec.hilotto.ui.view.fragment.MyFragment
import com.imaec.hilotto.ui.view.fragment.RecommendFragment
import com.imaec.hilotto.ui.view.fragment.StatisticsFragment
import com.imaec.hilotto.viewmodel.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var activeFragment: Fragment

    private val fragmentHome = HomeFragment()
    private val fragmentStatistics = StatisticsFragment()
    private val fragmentRecommend = RecommendFragment()
    private val fragmentMy = MyFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()

        viewModel = getViewModel(MainViewModel::class.java)

        binding.apply {
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.viewModel
        }

        setFragment(fragmentHome)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment = when (item.itemId) {
            R.id.navigation_home -> fragmentHome
            R.id.navigation_statistics -> fragmentStatistics
            R.id.navigation_recommend -> fragmentRecommend
            R.id.navigation_my-> fragmentMy
            else -> fragmentHome
        }
        setFragment(fragment)
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

    private fun init() {
        supportFragmentManager.beginTransaction().add(R.id.frame, fragmentMy, getString(R.string.my)).hide(fragmentMy).commit()
        supportFragmentManager.beginTransaction().add(R.id.frame, fragmentRecommend, getString(R.string.recommend)).hide(fragmentRecommend).commit()
        supportFragmentManager.beginTransaction().add(R.id.frame, fragmentStatistics, getString(R.string.statistics)).hide(fragmentStatistics).commit()
        supportFragmentManager.beginTransaction().add(R.id.frame, fragmentHome, getString(R.string.home)).commit()
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
