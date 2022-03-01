package com.imaec.hilotto.ui.lately

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.imaec.hilotto.model.LatelyResultVo
import com.imaec.hilotto.model.LottoVo
import com.imaec.hilotto.ui.view.dialog.SearchDialog
import com.imaec.hilotto.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LatelyResultActivity :
    BaseActivity<ActivityLatelyResultBinding>(R.layout.activity_lately_result) {

    private val viewModel by viewModels<LatelyResultViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupLoadingObserver(viewModel)
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

            val diffUtil = object : DiffUtil.ItemCallback<LatelyResultVo>() {
                override fun areItemsTheSame(
                    oldItem: LatelyResultVo,
                    newItem: LatelyResultVo
                ): Boolean = oldItem.round == newItem.round

                override fun areContentsTheSame(
                    oldItem: LatelyResultVo,
                    newItem: LatelyResultVo
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
                        SearchDialog(
                            context = this@LatelyResultActivity,
                            title = getString(R.string.search_round),
                            hint = getString(R.string.msg_search_hint_round),
                            searchCallback = { keyword ->
                                viewModel.checkSearchRound(keyword)
                            }
                        ).show()
                    }
                    is LatelyState.SuccessCheck -> {
                        binding.rvLatelyResult.scrollToPosition(it.position)
                    }
                    is LatelyState.FailCheck -> {
                        toast(it.message)
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

        fun createBundle(lottoList: List<LottoVo>, position: Int = 0): Bundle = bundleOf(
            LOTTO_LIST to lottoList,
            POSITION to position
        )
    }
}
