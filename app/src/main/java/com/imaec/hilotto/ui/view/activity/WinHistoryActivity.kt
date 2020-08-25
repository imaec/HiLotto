package com.imaec.hilotto.ui.view.activity

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.imaec.hilotto.EXTRA_LIST_LOTTO
import com.imaec.hilotto.EXTRA_MY_NUMBER
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseActivity
import com.imaec.hilotto.databinding.ActivityWinHistoryBinding
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.room.entity.NumberEntity
import com.imaec.hilotto.ui.util.NumbersDecoration
import com.imaec.hilotto.viewmodel.WinHistoryViewModel

@Suppress("UNCHECKED_CAST")
class WinHistoryActivity : BaseActivity<ActivityWinHistoryBinding>(R.layout.activity_win_history) {

    private lateinit var winHistoryViewModel: WinHistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        winHistoryViewModel = getViewModel(WinHistoryViewModel::class.java)

        binding.apply {
            lifecycleOwner = this@WinHistoryActivity
            winHistoryViewModel = this@WinHistoryActivity.winHistoryViewModel
            recyclerWinHistory.layoutManager = LinearLayoutManager(this@WinHistoryActivity)
            recyclerWinHistory.addItemDecoration(NumbersDecoration(this@WinHistoryActivity, 6, 12))
        }

        winHistoryViewModel.apply {
            setListLotto(intent.getSerializableExtra(EXTRA_LIST_LOTTO) as ArrayList<LottoDTO>)
            setMyNumber(intent.getSerializableExtra(EXTRA_MY_NUMBER) as NumberEntity)
            checkWin()
        }
    }
}