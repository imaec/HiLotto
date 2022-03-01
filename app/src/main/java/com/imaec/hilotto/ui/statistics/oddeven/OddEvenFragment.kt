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
import com.imaec.hilotto.model.OddEvenVo
import com.imaec.hilotto.ui.main.LottoViewModel

class OddEvenFragment : BaseFragment<FragmentOddEvenBinding>(R.layout.fragment_odd_even) {

    private val viewModel by viewModels<OddEvenViewModel>()
    private val lottoViewModel by activityViewModels<LottoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupLoadingObserver(viewModel, lottoViewModel)
        setupRecyclerView()
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
            val diffUtil = object : DiffUtil.ItemCallback<OddEvenVo>() {
                override fun areItemsTheSame(oldItem: OddEvenVo, newItem: OddEvenVo): Boolean =
                    oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: OddEvenVo,
                    newItem: OddEvenVo
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

    private fun setupObserver() {
        lottoViewModel.lottoList.observe(viewLifecycleOwner) {
            viewModel.setStatisticsNo(lottoViewModel.statisticsNo.value ?: 20)
            viewModel.setOddEven(it)
        }
    }
}
