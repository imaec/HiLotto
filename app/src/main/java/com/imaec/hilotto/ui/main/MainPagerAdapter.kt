package com.imaec.hilotto.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.imaec.hilotto.ui.home.HomeFragment
import com.imaec.hilotto.ui.view.fragment.MyFragment
import com.imaec.hilotto.ui.view.fragment.RecommendFragment
import com.imaec.hilotto.ui.view.fragment.SettingFragment
import com.imaec.hilotto.ui.statistics.StatisticsFragment

class MainPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> StatisticsFragment()
            2 -> RecommendFragment()
            3 -> MyFragment()
            else -> SettingFragment()
        }
    }
}
