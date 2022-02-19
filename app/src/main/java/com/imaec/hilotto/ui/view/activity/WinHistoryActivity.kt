package com.imaec.hilotto.ui.view.activity

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.activity.viewModels
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
import dagger.hilt.android.AndroidEntryPoint

@Suppress("UNCHECKED_CAST")
@AndroidEntryPoint
class WinHistoryActivity : BaseActivity<ActivityWinHistoryBinding>(R.layout.activity_win_history) {

    private val viewModel by viewModels<WinHistoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            vm = this@WinHistoryActivity.viewModel
            recyclerWinHistory.layoutManager = LinearLayoutManager(this@WinHistoryActivity)
            recyclerWinHistory.addItemDecoration(NumbersDecoration(this@WinHistoryActivity, 6, 12))
        }

        viewModel.apply {
            setListLotto(intent.getSerializableExtra(EXTRA_LIST_LOTTO) as ArrayList<LottoDTO>)
            setMyNumber(intent.getSerializableExtra(EXTRA_MY_NUMBER) as NumberEntity)
            checkWin()
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.image_sort -> {
                showPopup(view)
            }
        }
    }

    private fun showPopup(view: View) {
        PopupMenu(this, view).apply {
            menuInflater.inflate(R.menu.menu_sort, menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.option_default -> { // 기본
                        viewModel.sort(false)
                        true
                    }
                    R.id.option_win -> { // 순위순
                        viewModel.sort(isAcs = false, isWinSort = true)
                        true
                    }
                    R.id.option_acs -> { // 과거순
                        viewModel.sort(true)
                        true
                    }
                    R.id.option_desc -> { // 최신순
                        viewModel.sort(false)
                        true
                    }
                    else -> false
                }
            }
            show()
        }
    }
}
