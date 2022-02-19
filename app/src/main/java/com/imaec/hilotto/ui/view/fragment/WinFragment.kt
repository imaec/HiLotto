package com.imaec.hilotto.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentWinBinding
import com.imaec.hilotto.utils.SharedPreferenceUtil
import com.imaec.hilotto.viewmodel.LottoViewModel
import com.imaec.hilotto.viewmodel.WinViewModel

class WinFragment : BaseFragment<FragmentWinBinding>(R.layout.fragment_win) {

    private val viewModel by viewModels<WinViewModel>()
    private val lottoViewModel by activityViewModels<LottoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = this@WinFragment
            vm = this@WinFragment.viewModel
            lottoVm = this@WinFragment.lottoViewModel
        }

        viewModel.setStatisticsNo(
            SharedPreferenceUtil.getInt(
                requireContext(),
                SharedPreferenceUtil.KEY.PREF_SETTING_STATISTICS,
                20
            )
        )

        lottoViewModel.listResult.observe(
            requireActivity(),
            {
                viewModel.setWinInfo(it)
            }
        )
    }
}
