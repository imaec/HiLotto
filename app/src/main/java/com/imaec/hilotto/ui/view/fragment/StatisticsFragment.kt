package com.imaec.hilotto.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentStatisticsBinding
import com.imaec.hilotto.ui.adapter.StatisticsPagerAdapter
import com.imaec.hilotto.viewmodel.StatisticsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>(R.layout.fragment_statistics) {

    private val viewModel by viewModels<StatisticsViewModel>()
    private lateinit var pagerAdapter: StatisticsPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        if (!hidden) {
            init()
        }
    }

    fun init() {
//        viewModel = getViewModel(StatisticsViewModel::class.java)
        pagerAdapter = StatisticsPagerAdapter(childFragmentManager, lifecycle)

        binding.apply {
            lifecycleOwner = this@StatisticsFragment
            vm = this@StatisticsFragment.viewModel
            pagerStatistics.adapter = pagerAdapter
            TabLayoutMediator(tabStatistics, pagerStatistics) { tab, position ->
                tab.text = resources.getStringArray(R.array.tabs_statistics)[position]
            }.attach()
        }
    }
}
