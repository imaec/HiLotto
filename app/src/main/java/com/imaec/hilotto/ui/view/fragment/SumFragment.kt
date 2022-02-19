package com.imaec.hilotto.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentSumBinding
import com.imaec.hilotto.ui.util.SumDecoration
import com.imaec.hilotto.utils.SharedPreferenceUtil
import com.imaec.hilotto.viewmodel.LottoViewModel
import com.imaec.hilotto.viewmodel.SumViewModel

class SumFragment : BaseFragment<FragmentSumBinding>(R.layout.fragment_sum) {

    private val viewModel by viewModels<SumViewModel>()
    private val lottoViewModel by activityViewModels<LottoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = this@SumFragment
            vm = this@SumFragment.viewModel
            lottoVm = this@SumFragment.lottoViewModel
            recyclerSum.layoutManager = LinearLayoutManager(context)
            recyclerSum.addItemDecoration(SumDecoration(requireContext()))
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
                viewModel.setListSum(it)
                binding.checkIncludeBonus.setOnCheckedChangeListener { compoundButton, b ->
                    viewModel.setListSum(it, b)
                }
            }
        )
    }
}
