package com.imaec.hilotto.ui.winhistory

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imaec.hilotto.BR
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseActivity
import com.imaec.hilotto.base.BaseSingleViewAdapter
import com.imaec.hilotto.databinding.ActivityWinHistoryBinding
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.model.MyNumberDTO
import dagger.hilt.android.AndroidEntryPoint

@Suppress("UNCHECKED_CAST")
@AndroidEntryPoint
class WinHistoryActivity : BaseActivity<ActivityWinHistoryBinding>(R.layout.activity_win_history) {

    private val viewModel by viewModels<WinHistoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupRecyclerView()
        setupData()
        setupObserver()
    }

    private fun setupBinding() {
        with(binding) {
            vm = viewModel
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvWinHistory) {
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
                layoutResId = R.layout.item_win_history,
                bindingItemId = BR.item,
                viewModel = mapOf(BR.vm to viewModel),
                diffUtil = diffUtil
            )
        }
    }

    private fun setupData() {
        viewModel.checkWin()
    }

    private fun setupObserver() {
        viewModel.state.observe(this) {
            when (it) {
                WinHistoryState.OnClickSort -> {
                    showPopup(binding.ivSort)
                }
            }
        }
    }

    private fun showPopup(view: View) {
        PopupMenu(this, view).apply {
            menuInflater.inflate(R.menu.menu_sort, menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.option_default -> { // 기본
                        viewModel.sort(isAcs = false)
                        true
                    }
                    R.id.option_win -> { // 순위순
                        viewModel.sort(isAcs = false, isWinSort = true)
                        true
                    }
                    R.id.option_acs -> { // 과거순
                        viewModel.sort(isAcs = true)
                        true
                    }
                    R.id.option_desc -> { // 최신순
                        viewModel.sort(isAcs = false)
                        true
                    }
                    else -> false
                }
            }
            show()
        }
    }

    companion object {
        const val LOTTO_LIST = "lottoList"
        const val MY_NUMBER = "myNumber"

        fun createBundle(lottoList: List<LottoDTO>, myNumber: MyNumberDTO): Bundle = bundleOf(
            LOTTO_LIST to lottoList,
            MY_NUMBER to myNumber
        )
    }
}
