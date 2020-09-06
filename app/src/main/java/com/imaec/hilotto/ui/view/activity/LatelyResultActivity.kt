package com.imaec.hilotto.ui.view.activity

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.imaec.hilotto.EXTRA_LIST_LOTTO
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseActivity
import com.imaec.hilotto.databinding.ActivityLatelyResultBinding
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.viewmodel.LatelyResultViewModel

@Suppress("UNCHECKED_CAST")
class LatelyResultActivity : BaseActivity<ActivityLatelyResultBinding>(R.layout.activity_lately_result) {

    private lateinit var latelyResultViewModel: LatelyResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        latelyResultViewModel = getViewModel(LatelyResultViewModel::class.java)

        binding.apply {
            lifecycleOwner = this@LatelyResultActivity
            latelyResultViewModel =  this@LatelyResultActivity.latelyResultViewModel
        }

        latelyResultViewModel.apply {
            setListLatelyResult(intent.getSerializableExtra(EXTRA_LIST_LOTTO) as ArrayList<LottoDTO>)
            listLatelyResult.observe(this@LatelyResultActivity, Observer {
                Log.d(TAG, "    ## size : ${it.size}")
            })
        }
    }
}