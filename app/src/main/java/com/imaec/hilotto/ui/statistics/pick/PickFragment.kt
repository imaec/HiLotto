package com.imaec.hilotto.ui.statistics.pick

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
import com.imaec.hilotto.databinding.FragmentPickBinding
import com.imaec.hilotto.ui.main.LottoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PickFragment : BaseFragment<FragmentPickBinding>(R.layout.fragment_pick) {

    private val viewModel by viewModels<PickViewModel>()
    private val lottoViewModel by activityViewModels<LottoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupLoadingObserver(viewModel, lottoViewModel)
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
        with(binding) {
            listOf(rvNopick1, rvNopick2, rvNopick3, rvNopick4, rvNopick5).forEach {
                val diffUtil = object : DiffUtil.ItemCallback<Int>() {
                    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean =
                        oldItem == newItem

                    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean =
                        oldItem == newItem
                }

                val animator = it.itemAnimator
                if (animator is SimpleItemAnimator) {
                    animator.supportsChangeAnimations = false
                }

                it.adapter = BaseSingleViewAdapter(
                    layoutResId = R.layout.item_nopick,
                    bindingItemId = BR.item,
                    viewModel = mapOf(),
                    diffUtil = diffUtil
                )
            }
        }
    }

    private fun setupListener() {
        binding.cbIncludeBonus.setOnCheckedChangeListener { _, b ->
            viewModel.setPickedNum(lottoViewModel.lottoList.value, b)
            viewModel.setNoPickNum(lottoViewModel.lottoList.value, b)
        }
    }

    private fun setupObserver() {
        lottoViewModel.lottoList.observe(viewLifecycleOwner) {
            viewModel.setStatisticsNo(lottoViewModel.statisticsNo.value ?: 20)
            viewModel.setPickedNum(it)
            viewModel.setNoPickNum(it)
        }
    }
}
