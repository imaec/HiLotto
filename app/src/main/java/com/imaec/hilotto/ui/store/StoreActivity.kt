package com.imaec.hilotto.ui.store

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.gms.ads.AdRequest
import com.imaec.hilotto.BR
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseActivity
import com.imaec.hilotto.base.BaseSingleViewAdapter
import com.imaec.hilotto.databinding.ActivityStoreBinding
import com.imaec.hilotto.model.StoreDTO
import com.imaec.hilotto.ui.view.dialog.CopyDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@Suppress("UNCHECKED_CAST")
@AndroidEntryPoint
class StoreActivity : BaseActivity<ActivityStoreBinding>(R.layout.activity_store) {

    private val viewModel by viewModels<StoreViewModel>()

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
        binding.adStore.loadAd(AdRequest.Builder().build())
    }

    private fun setupRecyclerView() {
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
                viewModel = mapOf(BR.vm to viewModel),
                diffUtil = diffUtil
            )
        }
    }

    private fun setupObserver() {
        viewModel.state.observe(this) {
            when (it) {
                is StoreState.OnClickStore -> {
                    CopyDialog(this@StoreActivity).apply {
                        setTitle(getString(R.string.copy))
                        setOnCopyStoreClickListener { _ ->
                            copy(getString(R.string.store), it.store.storeName)
                            dismiss()
                        }
                        setOnCopyAddressClickListener { _ ->
                            copy(getString(R.string.store_address), it.store.address)
                            dismiss()
                        }
                        show()
                    }
                }
            }
        }
    }

    private fun copy(title: String, text: String) {
        (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager)
            .setPrimaryClip(ClipData.newPlainText(title, text))
        Toast.makeText(
            this@StoreActivity,
            "[$text]이(가)\n복사되었습니다.",
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        const val CUR_DRW_NO = "curDrwNo"
        const val STORE_LIST = "storeList"

        fun createBundle(curDrwNo: Int, storeList: List<StoreDTO>): Bundle = bundleOf(
            CUR_DRW_NO to curDrwNo,
            STORE_LIST to storeList
        )
    }
}
