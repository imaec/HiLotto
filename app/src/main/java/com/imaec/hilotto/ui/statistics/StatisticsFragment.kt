package com.imaec.hilotto.ui.statistics

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentStatisticsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>(R.layout.fragment_statistics) {

    private val viewModel by viewModels<StatisticsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupLoadingObserver(viewModel)
        setupLayout()
    }

    private fun setupBinding() {
        with(binding) {
            vm = viewModel
        }
    }

    private fun setupLayout() {
        with(binding) {
            vpStatistics.adapter = StatisticsPagerAdapter(requireActivity())
            TabLayoutMediator(tlStatistics, vpStatistics) { tab, position ->
                tab.text = resources.getStringArray(R.array.tabs_statistics)[position]
            }.attach()
        }
    }
}
