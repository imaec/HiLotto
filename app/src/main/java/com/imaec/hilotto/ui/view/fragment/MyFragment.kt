package com.imaec.hilotto.ui.view.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.imaec.hilotto.EXTRA_LIST_LOTTO
import com.imaec.hilotto.EXTRA_MY_NUMBER
import com.imaec.hilotto.EXTRA_NUMBER_1
import com.imaec.hilotto.EXTRA_NUMBER_2
import com.imaec.hilotto.EXTRA_NUMBER_3
import com.imaec.hilotto.EXTRA_NUMBER_4
import com.imaec.hilotto.EXTRA_NUMBER_5
import com.imaec.hilotto.EXTRA_NUMBER_6
import com.imaec.hilotto.EXTRA_NUMBER_ID
import com.imaec.hilotto.R
import com.imaec.hilotto.REQUEST_EDIT_NUMBER
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentMyBinding
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.room.entity.NumberEntity
import com.imaec.hilotto.ui.util.NumbersDecoration
import com.imaec.hilotto.ui.view.activity.EditNumberActivity
import com.imaec.hilotto.ui.view.activity.WinHistoryActivity
import com.imaec.hilotto.ui.view.dialog.CommonDialog
import com.imaec.hilotto.ui.view.dialog.EditDialog
import com.imaec.hilotto.ui.main.LottoViewModel
import com.imaec.hilotto.utils.toast
import com.imaec.hilotto.viewmodel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFragment : BaseFragment<FragmentMyBinding>(R.layout.fragment_my) {

    private val viewModel by viewModels<MyViewModel>()
    private val lottoViewModel by activityViewModels<LottoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            vm = viewModel
            recyclerMyNumbers.layoutManager = LinearLayoutManager(context)
            recyclerMyNumbers.addItemDecoration(NumbersDecoration(requireContext(), 6, 12))
            adView.loadAd(AdRequest.Builder().build())
        }

        viewModel.apply {
            listNumber.observe(
                requireActivity(),
                {
                    lottoViewModel.lottoList.value?.let {
                        checkWin(it[0])
                    }
                }
            )
            setOnNumberClickListener { entity ->
                if (entity !is NumberEntity) {
                    toast(R.string.msg_unknown_error)
                    return@setOnNumberClickListener
                }
                lottoViewModel.lottoList.value?.let {
                    showAd(R.string.ad_id_history_front, true) {
                        startActivity(
                            Intent(context, WinHistoryActivity::class.java).apply {
                                putExtra(EXTRA_LIST_LOTTO, it as ArrayList<LottoDTO>)
                                putExtra(EXTRA_MY_NUMBER, entity)
                            }
                        )
                    }
                }
            }
            setOnNumberLongClickListener { entity ->
                if (entity !is NumberEntity) {
                    toast(R.string.msg_unknown_error)
                    return@setOnNumberLongClickListener
                }
                EditDialog(requireContext()).apply {
                    setTitle(context.getString(R.string.edit))
                    setOnEditClickListener(
                        View.OnClickListener {
                            dismiss()
                            startActivityForResult(
                                Intent(context, EditNumberActivity::class.java).apply {
                                    putExtra(EXTRA_NUMBER_ID, entity.numberId)
                                    putExtra(EXTRA_NUMBER_1, entity.number1)
                                    putExtra(EXTRA_NUMBER_2, entity.number2)
                                    putExtra(EXTRA_NUMBER_3, entity.number3)
                                    putExtra(EXTRA_NUMBER_4, entity.number4)
                                    putExtra(EXTRA_NUMBER_5, entity.number5)
                                    putExtra(EXTRA_NUMBER_6, entity.number6)
                                },
                                REQUEST_EDIT_NUMBER
                            )
                        }
                    )
                    setOnDeleteClickListener {
                        dismiss()
                        CommonDialog(context, context.getString(R.string.msg_remove_number)).apply {
                            setOnOkClickListener(
                                View.OnClickListener {
                                    viewModel.deleteNumber(entity)
                                    dismiss()
                                }
                            )
                            show()
                        }
                    }
                    show()
                }
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        if (!hidden) {
            viewModel.getNumbers()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != RESULT_OK) return

        if (requestCode == REQUEST_EDIT_NUMBER) {
            viewModel.getNumbers()
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.image_setting -> {
            }
        }
    }
}
