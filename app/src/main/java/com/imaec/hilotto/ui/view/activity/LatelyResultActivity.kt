package com.imaec.hilotto.ui.view.activity

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.gms.ads.AdRequest
import com.imaec.hilotto.EXTRA_LATELY_RESULT_POSITION
import com.imaec.hilotto.EXTRA_LIST_LOTTO
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseActivity
import com.imaec.hilotto.databinding.ActivityLatelyResultBinding
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.ui.view.dialog.InputDialog
import com.imaec.hilotto.viewmodel.LatelyResultViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_search.*

@Suppress("UNCHECKED_CAST")
@AndroidEntryPoint
class LatelyResultActivity :
    BaseActivity<ActivityLatelyResultBinding>(R.layout.activity_lately_result) {

    private val viewModel by viewModels<LatelyResultViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            lifecycleOwner = this@LatelyResultActivity
            vm = this@LatelyResultActivity.viewModel
            adView.loadAd(AdRequest.Builder().build())
        }

        viewModel.apply {
            setListLatelyResult(
                intent.getSerializableExtra(EXTRA_LIST_LOTTO) as ArrayList<LottoDTO>
            )
            listLatelyResult.observe(
                this@LatelyResultActivity,
                {
                    Handler().postDelayed(
                        {
                            binding.vpLatelyResult.currentItem =
                                intent.getIntExtra(EXTRA_LATELY_RESULT_POSITION, 0)
                        },
                        100
                    )
                }
            )
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.image_search -> {
                InputDialog(this).apply {
                    setTitle(getString(R.string.search_round))
                    setHint(getString(R.string.msg_search_hint_round))
                    setOnSearchClickListener {
                        val result = viewModel.checkSearchRound(edit_search.text.toString())
                        if (result == "OK") {
                            val searchRound = edit_search.text.toString().toInt()
                            val currentRound = viewModel.listLatelyResult.value!![0].drwNo
                            binding.vpLatelyResult.currentItem = currentRound - searchRound
                        } else {
                            Toast.makeText(this@LatelyResultActivity, result, Toast.LENGTH_SHORT)
                                .show()
                        }
                        dismiss()
                    }
                    show()
                }
            }
        }
    }
}
