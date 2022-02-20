package com.imaec.hilotto.ui.statistics.continues

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
import com.imaec.hilotto.databinding.FragmentContinuesBinding
import com.imaec.hilotto.model.ContinueDTO
import com.imaec.hilotto.ui.main.LottoViewModel
import com.imaec.hilotto.utils.SharedPreferenceUtil

class ContinuesFragment : BaseFragment<FragmentContinuesBinding>(R.layout.fragment_continues) {

    private val viewModel by viewModels<ContinuesViewModel>()
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
        with(binding.rvContinues) {
            val diffUtil = object : DiffUtil.ItemCallback<ContinueDTO>() {
                override fun areItemsTheSame(oldItem: ContinueDTO, newItem: ContinueDTO): Boolean =
                    oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: ContinueDTO,
                    newItem: ContinueDTO
                ): Boolean = oldItem == newItem
            }

            val animator = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            adapter = BaseSingleViewAdapter(
                layoutResId = R.layout.item_continues,
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
            viewModel.setPickedNum(it)
        }
    }
}
