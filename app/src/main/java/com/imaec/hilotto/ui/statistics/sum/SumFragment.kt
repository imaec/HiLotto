package com.imaec.hilotto.ui.statistics.sum

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imaec.hilotto.BR
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.base.BaseSingleViewAdapter
import com.imaec.hilotto.databinding.FragmentSumBinding
import com.imaec.hilotto.model.SumVo
import com.imaec.hilotto.utils.SharedPreferenceUtil
import com.imaec.hilotto.ui.main.LottoViewModel

class SumFragment : BaseFragment<FragmentSumBinding>(R.layout.fragment_sum) {

    private val viewModel by viewModels<SumViewModel>()
    private val lottoViewModel by activityViewModels<LottoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupRecyclerView()
        setupListener()
        setupObserver()
    }

    private fun setupBinding() {
        with(binding) {
            vm = viewModel
            lottoVm = lottoViewModel
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvSum) {
            val diffUtil = object : DiffUtil.ItemCallback<SumVo>() {
                override fun areItemsTheSame(oldItem: SumVo, newItem: SumVo): Boolean =
                    oldItem.round == newItem.round

                override fun areContentsTheSame(oldItem: SumVo, newItem: SumVo): Boolean =
                    oldItem == newItem
            }

            val animator = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            adapter = BaseSingleViewAdapter(
                layoutResId = R.layout.item_sum,
                bindingItemId = BR.item,
                viewModel = mapOf(BR.vm to viewModel),
                diffUtil = diffUtil
            )
        }
    }

    private fun setupListener() {
        binding.cbIncludeBonus.setOnCheckedChangeListener { _, b ->
            viewModel.setSumList(lottoViewModel.lottoList.value, b)
        }
    }

    private fun setupObserver() {
        lottoViewModel.lottoList.observe(viewLifecycleOwner) {
            viewModel.setStatisticsNo(
                SharedPreferenceUtil.getInt(
                    context = requireContext(),
                    key = SharedPreferenceUtil.KEY.PREF_SETTING_STATISTICS,
                    def = 20
                )
            )
            viewModel.setSumList(it)
        }
    }
}
