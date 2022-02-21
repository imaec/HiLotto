package com.imaec.hilotto.ui.my

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.gms.ads.AdRequest
import com.imaec.hilotto.BR
import com.imaec.hilotto.EXTRA_NUMBER_1
import com.imaec.hilotto.EXTRA_NUMBER_2
import com.imaec.hilotto.EXTRA_NUMBER_3
import com.imaec.hilotto.EXTRA_NUMBER_4
import com.imaec.hilotto.EXTRA_NUMBER_5
import com.imaec.hilotto.EXTRA_NUMBER_6
import com.imaec.hilotto.EXTRA_NUMBER_ID
import com.imaec.hilotto.R
import com.imaec.hilotto.REQUEST_EDIT_NUMBER
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.base.BaseSingleViewAdapter
import com.imaec.hilotto.databinding.FragmentMyBinding
import com.imaec.hilotto.model.MyNumberDTO
import com.imaec.hilotto.ui.view.activity.EditNumberActivity
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
        setupAd()
        setupRecyclerView()
        setupObserver()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != RESULT_OK) return

        if (requestCode == REQUEST_EDIT_NUMBER) {
//            viewModel.getNumbers()
        }
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
            val diffUtil = object : DiffUtil.ItemCallback<MyNumberDTO>() {
                override fun areItemsTheSame(
                    oldItem: MyNumberDTO,
                    newItem: MyNumberDTO
                ): Boolean = oldItem.numberId == newItem.numberId

                override fun areContentsTheSame(
                    oldItem: MyNumberDTO,
                    newItem: MyNumberDTO
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
                            showAd(R.string.ad_id_history_front, true) {
                                startActivity<WinHistoryActivity>(
                                    WinHistoryActivity.createBundle(
                                        lottoList = list,
                                        myNumber = it.myNumber
                                    )
                                )
                            }
                        }
                    }
                    is MyState.OnLongClickNumber -> {
                        EditDialog(requireContext()).apply {
                            setTitle(context.getString(R.string.edit))
                            setOnEditClickListener { _ ->
                                dismiss()
                                startActivityForResult(
                                    Intent(context, EditNumberActivity::class.java).apply {
                                        putExtra(EXTRA_NUMBER_ID, it.myNumber.numberId)
                                        putExtra(EXTRA_NUMBER_1, it.myNumber.number1)
                                        putExtra(EXTRA_NUMBER_2, it.myNumber.number2)
                                        putExtra(EXTRA_NUMBER_3, it.myNumber.number3)
                                        putExtra(EXTRA_NUMBER_4, it.myNumber.number4)
                                        putExtra(EXTRA_NUMBER_5, it.myNumber.number5)
                                        putExtra(EXTRA_NUMBER_6, it.myNumber.number6)
                                    },
                                    REQUEST_EDIT_NUMBER
                                )
                            }
                            setOnDeleteClickListener { _ ->
                                dismiss()
                                CommonDialog(
                                    context,
                                    context.getString(R.string.msg_remove_number)
                                ).apply {
                                    setOnOkClickListener { _ ->
                                        viewModel.deleteNumber(it.myNumber)
                                        dismiss()
                                    }
                                    show()
                                }
                            }
                            show()
                        }
                    }
                }
            }
            numberEntityList.observe(owner) {
                lottoViewModel.lottoList.value?.let {
                    checkWin(it[0])
                }
            }
        }
    }
}
