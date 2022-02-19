package com.imaec.hilotto.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentOddEvenBinding
import com.imaec.hilotto.ui.util.NumbersDecoration
import com.imaec.hilotto.utils.SharedPreferenceUtil
import com.imaec.hilotto.viewmodel.LottoViewModel
import com.imaec.hilotto.viewmodel.OddEvenViewModel

class OddEvenFragment : BaseFragment<FragmentOddEvenBinding>(R.layout.fragment_odd_even) {

    private val viewModel by viewModels<OddEvenViewModel>()
    private val lottoViewModel by activityViewModels<LottoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            vm = this@OddEvenFragment.viewModel
            lottoVm = this@OddEvenFragment.lottoViewModel
            recyclerOddEven.layoutManager = LinearLayoutManager(context)
            recyclerOddEven.addItemDecoration(NumbersDecoration(requireContext()))
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
                viewModel.setOddEven(it)
            }
        )
    }
}
