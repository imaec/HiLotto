package com.imaec.hilotto.ui.statistics.oddeven

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
import com.imaec.hilotto.databinding.FragmentOddEvenBinding
import com.imaec.hilotto.model.OddEvenDTO
import com.imaec.hilotto.utils.SharedPreferenceUtil
import com.imaec.hilotto.ui.main.LottoViewModel

class OddEvenFragment : BaseFragment<FragmentOddEvenBinding>(R.layout.fragment_odd_even) {

    private val viewModel by viewModels<OddEvenViewModel>()
    private val lottoViewModel by activityViewModels<LottoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupRecyclerView()
        setupData()
        setupObserver()
    }

    private fun setupBinding() {
        with(binding) {
            vm = viewModel
            lottoVm = lottoViewModel
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvOddEven) {
            val diffUtil = object : DiffUtil.ItemCallback<OddEvenDTO>() {
                override fun areItemsTheSame(oldItem: OddEvenDTO, newItem: OddEvenDTO): Boolean =
                    oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: OddEvenDTO,
                    newItem: OddEvenDTO
                ): Boolean = oldItem == newItem
            }

            val animator = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            adapter = BaseSingleViewAdapter(
                layoutResId = R.layout.item_odd_even,
                bindingItemId = BR.item,
                viewModel = mapOf(BR.vm to viewModel),
                diffUtil = diffUtil
            )
        }
    }

    private fun setupData() {
        viewModel.setStatisticsNo(
            SharedPreferenceUtil.getInt(
                context = requireContext(),
                key = SharedPreferenceUtil.KEY.PREF_SETTING_STATISTICS,
                def = 20
            )
        )
    }

    private fun setupObserver() {
        lottoViewModel.lottoList.observe(viewLifecycleOwner) {
            viewModel.setOddEven(it)
        }
    }
}
