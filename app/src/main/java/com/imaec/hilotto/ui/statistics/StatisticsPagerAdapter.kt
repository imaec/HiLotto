package com.imaec.hilotto.ui.statistics

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.imaec.hilotto.ui.statistics.continues.ContinuesFragment
import com.imaec.hilotto.ui.statistics.oddeven.OddEvenFragment
import com.imaec.hilotto.ui.statistics.pick.PickFragment
import com.imaec.hilotto.ui.statistics.sum.SumFragment
import com.imaec.hilotto.ui.view.fragment.WinFragment

class StatisticsPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SumFragment()
            1 -> PickFragment()
            2 -> ContinuesFragment()
            3 -> OddEvenFragment()
            else -> WinFragment()
        }
    }
}
