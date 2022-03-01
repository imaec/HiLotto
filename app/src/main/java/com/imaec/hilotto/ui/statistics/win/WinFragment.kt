package com.imaec.hilotto.ui.statistics.win

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentWinBinding
import com.imaec.hilotto.ui.main.LottoViewModel

class WinFragment : BaseFragment<FragmentWinBinding>(R.layout.fragment_win) {

    private val viewModel by viewModels<WinViewModel>()
    private val lottoViewModel by activityViewModels<LottoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupLoadingObserver(viewModel, lottoViewModel)
        setupObserver()
    }

    private fun setupBinding() {
        with(binding) {
            vm = viewModel
            lottoVm = lottoViewModel
        }
    }

    private fun setupObserver() {
        lottoViewModel.lottoList.observe(viewLifecycleOwner) {
            viewModel.setStatisticsNo(lottoViewModel.statisticsNo.value ?: 20)
            viewModel.setWinInfo(it)
        }
    }
}
