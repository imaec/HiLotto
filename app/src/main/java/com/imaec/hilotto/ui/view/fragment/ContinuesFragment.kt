package com.imaec.hilotto.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentContinuesBinding
import com.imaec.hilotto.ui.util.NumbersDecoration
import com.imaec.hilotto.utils.SharedPreferenceUtil
import com.imaec.hilotto.viewmodel.ContinuesViewModel
import com.imaec.hilotto.viewmodel.LottoViewModel

class ContinuesFragment : BaseFragment<FragmentContinuesBinding>(R.layout.fragment_continues) {

    private val viewModel by viewModels<ContinuesViewModel>()
    private val lottoViewModel by activityViewModels<LottoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = this@ContinuesFragment
            vm = viewModel
            lottoVm = lottoViewModel
            recyclerContinues.layoutManager = LinearLayoutManager(context)
            recyclerContinues.addItemDecoration(NumbersDecoration(requireContext()))
        }

        viewModel.setStatisticsNo(
            SharedPreferenceUtil.getInt(
                requireContext(),
                SharedPreferenceUtil.KEY.PREF_SETTING_STATISTICS,
                20
            )
        )

        lottoViewModel.lottoList.observe(
            requireActivity(),
            {
                viewModel.setPickedNum(it)
            }
        )
    }
}
