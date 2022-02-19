package com.imaec.hilotto.ui.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.imaec.hilotto.EXTRA_CURRENT_ROUND
import com.imaec.hilotto.EXTRA_LATELY_RESULT_POSITION
import com.imaec.hilotto.EXTRA_LIST_LOTTO
import com.imaec.hilotto.EXTRA_LIST_STORE
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentHomeBinding
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.model.StoreDTO
import com.imaec.hilotto.ui.util.LatelyResultDecoration
import com.imaec.hilotto.ui.view.activity.LatelyResultActivity
import com.imaec.hilotto.ui.view.activity.StoreActivity
import com.imaec.hilotto.viewmodel.HomeViewModel
import com.imaec.hilotto.viewmodel.LottoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel by viewModels<HomeViewModel>()
    private val lottoViewModel by activityViewModels<LottoViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = this@HomeFragment
            vm = viewModel
            lottoVm = lottoViewModel
            recyclerLatelyResult.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerLatelyResult.addItemDecoration(LatelyResultDecoration(requireContext()))
            adView.loadAd(AdRequest.Builder().build())
        }

        viewModel.apply {
            setOnItemClickListener { dto ->
                lottoViewModel.listResult.value?.let {
                    showAd(R.string.ad_id_lately_front, true) {
                        val position = it.indexOf(dto)
                        startActivity(
                            Intent(context, LatelyResultActivity::class.java).apply {
                                putExtra(EXTRA_LIST_LOTTO, it as ArrayList<LottoDTO>)
                                putExtra(EXTRA_LATELY_RESULT_POSITION, position)
                            }
                        )
                    }
                }
            }
        }

        lottoViewModel.listResult.observe(
            requireActivity(),
            {
                viewModel.setListLatelyResult(it)
            }
        )
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.text_lately_result_more -> {
                lottoViewModel.listResult.value?.let {
                    startActivity(
                        Intent(context, LatelyResultActivity::class.java).apply {
                            putExtra(EXTRA_LIST_LOTTO, it as ArrayList<LottoDTO>)
                        }
                    )
                }
            }
            R.id.text_store -> {
                lottoViewModel.listStore.value?.let {
                    startActivity(
                        Intent(context, StoreActivity::class.java).apply {
                            putExtra(EXTRA_CURRENT_ROUND, lottoViewModel.curDrwNo.value ?: 0)
                            putExtra(EXTRA_LIST_STORE, it as ArrayList<StoreDTO>)
                        }
                    )
                }
            }
        }
    }
}
