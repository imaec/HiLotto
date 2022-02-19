package com.imaec.hilotto.ui.lately

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.gms.ads.AdRequest
import com.imaec.hilotto.BR
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseActivity
import com.imaec.hilotto.base.BaseSingleViewAdapter
import com.imaec.hilotto.databinding.ActivityLatelyResultBinding
import com.imaec.hilotto.model.LatelyResultDTO
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.ui.view.dialog.InputDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LatelyResultActivity :
    BaseActivity<ActivityLatelyResultBinding>(R.layout.activity_lately_result) {

    private val viewModel by viewModels<LatelyResultViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupAd()
        setupRecyclerView()
        setupObserver()
    }

    private fun setupBinding() {
        binding.apply {
            vm = viewModel
        }
    }

    private fun setupAd() {
        binding.adLately.loadAd(AdRequest.Builder().build())
    }

    private fun setupRecyclerView() {
        with(binding.rvLatelyResult) {
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(this)

            val diffUtil = object : DiffUtil.ItemCallback<LatelyResultDTO>() {
                override fun areItemsTheSame(
                    oldItem: LatelyResultDTO,
                    newItem: LatelyResultDTO
                ): Boolean = oldItem.round == newItem.round

                override fun areContentsTheSame(
                    oldItem: LatelyResultDTO,
                    newItem: LatelyResultDTO
                ): Boolean = oldItem == newItem
            }

            val animator = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            adapter = BaseSingleViewAdapter(
                layoutResId = R.layout.item_lately_result_detail,
                bindingItemId = BR.item,
                viewModel = mapOf(),
                diffUtil = diffUtil
            )
        }
    }

    private fun setupObserver() {
        val owner = this
        with(viewModel) {
            state.observe(owner) {
                when (it) {
                    LatelyState.OnClickSearch -> {
                        InputDialog(this@LatelyResultActivity).apply {
                            setTitle(getString(R.string.search_round))
                            setHint(getString(R.string.msg_search_hint_round))
                            setOnSearchClickListener { keyword ->
                                viewModel.checkSearchRound(keyword)
                                dismiss()
                            }
                            show()
                        }
                    }
                    is LatelyState.SuccessCheck -> {
                        binding.rvLatelyResult.scrollToPosition(it.position)
                    }
                    is LatelyState.FailCheck -> {
                        Toast
                            .makeText(this@LatelyResultActivity, it.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
            latelyResultList.observe(owner) {
                if (it.isNotEmpty()) {
                    Handler(Looper.getMainLooper()).postDelayed(
                        {
                            binding.rvLatelyResult.scrollToPosition(viewModel.position)
                        },
                        100
                    )
                }
            }
        }
    }

    companion object {
        const val LOTTO_LIST = "lottoList"
        const val POSITION = "position"

        fun createBundle(lottoList: List<LottoDTO>, position: Int = 0): Bundle = bundleOf(
            LOTTO_LIST to lottoList,
            POSITION to position
        )
    }
}
