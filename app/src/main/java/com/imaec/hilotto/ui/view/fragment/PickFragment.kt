package com.imaec.hilotto.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentPickBinding
import com.imaec.hilotto.ui.util.NumberDecoration
import com.imaec.hilotto.utils.SharedPreferenceUtil
import com.imaec.hilotto.viewmodel.LottoViewModel
import com.imaec.hilotto.viewmodel.PickViewModel

class PickFragment : BaseFragment<FragmentPickBinding>(R.layout.fragment_pick) {

    private val viewModel by viewModels<PickViewModel>()
    private val lottoViewModel by activityViewModels<LottoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = this@PickFragment
            vm = viewModel
            lottoVm = lottoViewModel
            recyclerNopick1.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(NumberDecoration(context))
            }
            recyclerNopick2.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(NumberDecoration(context))
            }
            recyclerNopick3.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(NumberDecoration(context))
            }
            recyclerNopick4.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(NumberDecoration(context))
            }
            recyclerNopick5.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(NumberDecoration(context))
            }
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
                viewModel.setNoPickNum(it)
                binding.checkIncludeBonus.setOnCheckedChangeListener { compoundButton, b ->
                    viewModel.setPickedNum(it, b)
                    viewModel.setNoPickNum(it, b)
                }
            }
        )
    }
}
