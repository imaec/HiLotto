package com.imaec.hilotto.ui.view.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.imaec.hilotto.EXTRA_CURRENT_ROUND
import com.imaec.hilotto.EXTRA_LIST_STORE
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseActivity
import com.imaec.hilotto.databinding.ActivityStoreBinding
import com.imaec.hilotto.model.StoreDTO
import com.imaec.hilotto.ui.util.StoreDecoration
import com.imaec.hilotto.ui.view.dialog.CopyDialog
import com.imaec.hilotto.viewmodel.StoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@Suppress("UNCHECKED_CAST")
@AndroidEntryPoint
class StoreActivity : BaseActivity<ActivityStoreBinding>(R.layout.activity_store) {

    private val viewModel by viewModels<StoreViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            lifecycleOwner = this@StoreActivity
            vm = this@StoreActivity.viewModel
            recyclerStore.layoutManager = LinearLayoutManager(this@StoreActivity)
            recyclerStore.addItemDecoration(StoreDecoration(this@StoreActivity))
            adView.loadAd(AdRequest.Builder().build())
        }

        viewModel.apply {
            setListStore(intent.getSerializableExtra(EXTRA_LIST_STORE) as ArrayList<StoreDTO>)
            setRound(intent.getIntExtra(EXTRA_CURRENT_ROUND, 0))
            setOnItemClickListener { item ->
                if (item is StoreDTO) {
                    CopyDialog(this@StoreActivity).apply {
                        setTitle(getString(R.string.copy))
                        setOnCopyStoreClickListener {
                            (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager)
                                .setPrimaryClip(
                                    ClipData.newPlainText(getString(R.string.store), item.storeName)
                                )
                            Toast.makeText(
                                this@StoreActivity,
                                "[${item.storeName}]이(가)\n복사되었습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                            dismiss()
                        }
                        setOnCopyAddressClickListener {
                            (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager)
                                .setPrimaryClip(
                                    ClipData.newPlainText(
                                        getString(R.string.store_address),
                                        item.address
                                    )
                                )
                            Toast.makeText(
                                this@StoreActivity,
                                "[${item.address}]이(가)\n복사되었습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                            dismiss()
                        }
                        show()
                    }
                }
            }
        }
    }
}
