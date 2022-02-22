package com.imaec.hilotto.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.gms.ads.AdRequest
import com.imaec.hilotto.BR
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.base.BaseSingleViewAdapter
import com.imaec.hilotto.databinding.FragmentHomeBinding
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.model.StoreDTO
import com.imaec.hilotto.ui.lately.LatelyResultActivity
import com.imaec.hilotto.ui.store.StoreActivity
import com.imaec.hilotto.utils.startActivity
import com.imaec.hilotto.ui.main.LottoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel by viewModels<HomeViewModel>()
    private val lottoViewModel by activityViewModels<LottoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupAd()
        setupRecyclerView()
        setupObserver()
    }

    private fun setupBinding() {
        with(binding) {
            vm = viewModel
            lottoVm = lottoViewModel
        }
    }

    private fun setupAd() {
        binding.adHome.loadAd(AdRequest.Builder().build())
    }

    private fun setupRecyclerView() {
        with(binding.rvLatelyResult) {
            val diffUtil = object : DiffUtil.ItemCallback<LottoDTO>() {
                override fun areItemsTheSame(oldItem: LottoDTO, newItem: LottoDTO): Boolean =
                    oldItem.drwNo == newItem.drwNo

                override fun areContentsTheSame(oldItem: LottoDTO, newItem: LottoDTO): Boolean =
                    oldItem == newItem
            }

            val animator = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            adapter = BaseSingleViewAdapter(
                layoutResId = R.layout.item_lately_result,
                bindingItemId = BR.item,
                viewModel = mapOf(
                    BR.vm to viewModel,
                    BR.lottoVm to lottoViewModel
                ),
                diffUtil = diffUtil
            )
        }

        with(binding.rvStore) {
            val diffUtil = object : DiffUtil.ItemCallback<StoreDTO>() {
                override fun areItemsTheSame(oldItem: StoreDTO, newItem: StoreDTO): Boolean =
                    oldItem == newItem

                override fun areContentsTheSame(oldItem: StoreDTO, newItem: StoreDTO): Boolean =
                    oldItem == newItem
            }

            val animator = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            adapter = BaseSingleViewAdapter(
                layoutResId = R.layout.item_store,
                bindingItemId = BR.item,
                viewModel = mapOf(),
                diffUtil = diffUtil
            )
        }
    }

    private fun setupObserver() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is HomeState.OnClickLately -> {
                    showAd(R.string.ad_id_lately_front, true) {
                        val position = it.lottoList.indexOf(it.lotto)
                        startActivity<LatelyResultActivity>(
                            LatelyResultActivity.createBundle(
                                lottoList = it.lottoList,
                                position = position
                            )
                        )
                    }
                }
                is HomeState.OnClickMore -> {
                    startActivity<LatelyResultActivity>(
                        LatelyResultActivity.createBundle(it.lottoList)
                    )
                }
                is HomeState.OnClickStore -> {
                    startActivity<StoreActivity>(
                        StoreActivity.createBundle(
                            curDrwNo = it.curDrwNo,
                            storeList = it.storeList
                        )
                    )
                }
            }
        }
        lottoViewModel.lottoList.observe(viewLifecycleOwner) {
            viewModel.setListLatelyResult(it)
        }
    }
}
