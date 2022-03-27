package com.imaec.hilotto.ui.my

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
import com.imaec.hilotto.databinding.FragmentMyBinding
import com.imaec.hilotto.model.MyNumberVo
import com.imaec.hilotto.ui.editnumber.EditNumberActivity
import com.imaec.hilotto.ui.winhistory.WinHistoryActivity
import com.imaec.hilotto.ui.view.dialog.CommonDialog
import com.imaec.hilotto.ui.view.dialog.EditDialog
import com.imaec.hilotto.ui.main.LottoViewModel
import com.imaec.hilotto.utils.startActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFragment : BaseFragment<FragmentMyBinding>(R.layout.fragment_my) {

    private val viewModel by viewModels<MyViewModel>()
    private val lottoViewModel by activityViewModels<LottoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupLoadingObserver(viewModel, lottoViewModel)
        setupAd()
        setupRecyclerView()
        setupObserver()
    }

    private fun setupBinding() {
        with(binding) {
            vm = viewModel
        }
    }

    private fun setupAd() {
        binding.adMy.loadAd(AdRequest.Builder().build())
    }

    private fun setupRecyclerView() {
        with(binding.rvMyNumbers) {
            val diffUtil = object : DiffUtil.ItemCallback<MyNumberVo>() {
                override fun areItemsTheSame(
                    oldItem: MyNumberVo,
                    newItem: MyNumberVo
                ): Boolean = oldItem.numberId == newItem.numberId

                override fun areContentsTheSame(
                    oldItem: MyNumberVo,
                    newItem: MyNumberVo
                ): Boolean = oldItem == newItem
            }

            val animator = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            adapter = BaseSingleViewAdapter(
                layoutResId = R.layout.item_my_number,
                bindingItemId = BR.item,
                viewModel = mapOf(BR.vm to viewModel),
                diffUtil = diffUtil
            )
        }
    }

    private fun setupObserver() {
        with(viewModel) {
            val owner = viewLifecycleOwner
            state.observe(owner) {
                when (it) {
                    is MyState.OnClickNumber -> {
                        lottoViewModel.lottoList.value?.let { list ->
                            showAd(
                                adId = R.string.ad_id_history_front,
                                isRandom = true,
                                onLoaded = {
                                    interstitialAd?.show(requireActivity())
                                },
                                onClosed = {
                                    startActivity<WinHistoryActivity>(
                                        WinHistoryActivity.createBundle(
                                            lottoList = list,
                                            myNumber = it.myNumber
                                        )
                                    )
                                }
                            )
                        }
                    }
                    is MyState.OnLongClickNumber -> {
                        EditDialog(
                            context = requireContext(),
                            editCallback = {
                                this@MyFragment.startActivity<EditNumberActivity>(
                                    EditNumberActivity.createBundle(it.myNumber)
                                )
                            },
                            deleteCallback = {
                                CommonDialog(
                                    context = requireContext(),
                                    message = getString(R.string.msg_remove_number),
                                    positiveCallback = {
                                        viewModel.deleteNumber(it.myNumber)
                                    }
                                ).show()
                            }
                        ).show()
                    }
                }
            }
            lottoViewModel.lottoList.observe(owner) { lottoList ->
                numberEntityList.observe(owner) {
                    checkWin(lottoList[0])
                }
            }
        }
    }
}
