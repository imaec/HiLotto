package com.imaec.hilotto.ui.view.fragment

import android.os.Bundle
import android.view.View
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentStatisticsBinding
import com.imaec.hilotto.viewmodel.StatisticsViewModel

class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>(R.layout.fragment_statistics) {

    private lateinit var viewModel: StatisticsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = getViewModel(StatisticsViewModel::class.java)

        binding.apply {
            lifecycleOwner = this@StatisticsFragment
            viewModel = this@StatisticsFragment.viewModel
        }
    }
}