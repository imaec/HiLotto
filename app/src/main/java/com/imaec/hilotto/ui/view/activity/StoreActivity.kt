package com.imaec.hilotto.ui.view.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.imaec.hilotto.EXTRA_CURRENT_ROUND
import com.imaec.hilotto.EXTRA_LIST_STORE
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseActivity
import com.imaec.hilotto.databinding.ActivityStoreBinding
import com.imaec.hilotto.model.StoreDTO
import com.imaec.hilotto.ui.util.StoreDecoration
import com.imaec.hilotto.viewmodel.StoreViewModel

@Suppress("UNCHECKED_CAST")
class StoreActivity : BaseActivity<ActivityStoreBinding>(R.layout.activity_store) {

    private lateinit var storeViewModel: StoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storeViewModel = getViewModel(StoreViewModel::class.java)

        binding.apply {
            lifecycleOwner = this@StoreActivity
            storeViewModel = this@StoreActivity.storeViewModel
            recyclerStore.layoutManager = LinearLayoutManager(this@StoreActivity)
            recyclerStore.addItemDecoration(StoreDecoration(this@StoreActivity))
            adView.loadAd(AdRequest.Builder().build())
        }

        storeViewModel.apply {
            setListStore(intent.getSerializableExtra(EXTRA_LIST_STORE) as ArrayList<StoreDTO>)
            setRound(intent.getIntExtra(EXTRA_CURRENT_ROUND, 0))
        }
    }
}