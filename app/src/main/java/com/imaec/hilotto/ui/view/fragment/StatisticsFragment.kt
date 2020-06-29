package com.imaec.hilotto.ui.view.fragment

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentStatisticsBinding
import com.imaec.hilotto.ui.view.activity.MainActivity
import com.imaec.hilotto.ui.view.adapter.StatisticsPagerAdapter
import com.imaec.hilotto.viewmodel.StatisticsViewModel

class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>(R.layout.fragment_statistics) {

    private lateinit var viewModel: StatisticsViewModel
    private lateinit var pagerAdapter: StatisticsPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        init()

        binding.apply {
            lifecycleOwner = this@StatisticsFragment
            viewModel = this@StatisticsFragment.viewModel
            pagerStatistics.adapter = pagerAdapter
            TabLayoutMediator(tabStatistics, pagerStatistics) { tab, position ->
                tab.text = resources.getStringArray(R.array.tabs_statistics)[position]
            }.attach()
        }
    }

    private fun init() {
        viewModel = getViewModel(StatisticsViewModel::class.java)
        pagerAdapter = StatisticsPagerAdapter(childFragmentManager, lifecycle)
    }
}