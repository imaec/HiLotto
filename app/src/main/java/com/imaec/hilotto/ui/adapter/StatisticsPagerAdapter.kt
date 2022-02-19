package com.imaec.hilotto.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.imaec.hilotto.ui.view.fragment.ContinuesFragment
import com.imaec.hilotto.ui.view.fragment.OddEvenFragment
import com.imaec.hilotto.ui.view.fragment.PickFragment
import com.imaec.hilotto.ui.view.fragment.SumFragment
import com.imaec.hilotto.ui.view.fragment.WinFragment

class StatisticsPagerAdapter(
    fragmentManager: FragmentManager,
    lifeCycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifeCycle) {

    private val listFragment =
        listOf(SumFragment(), PickFragment(), ContinuesFragment(), OddEvenFragment(), WinFragment())

    override fun getItemCount(): Int = listFragment.size

    override fun createFragment(position: Int): Fragment = listFragment[position]
}
