package com.imaec.hilotto.ui.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.firebase.analytics.FirebaseAnalytics
import com.imaec.hilotto.*
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentHomeBinding
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.model.StoreDTO
import com.imaec.hilotto.repository.FirebaseRepository
import com.imaec.hilotto.repository.LottoRepository
import com.imaec.hilotto.ui.util.LatelyResultDecoration
import com.imaec.hilotto.ui.view.activity.LatelyResultActivity
import com.imaec.hilotto.ui.view.activity.StoreActivity
import com.imaec.hilotto.ui.view.activity.WinHistoryActivity
import com.imaec.hilotto.viewmodel.HomeViewModel
import com.imaec.hilotto.viewmodel.LottoViewModel
import com.kakao.kakaolink.v2.KakaoLinkResponse
import com.kakao.kakaolink.v2.KakaoLinkService
import com.kakao.network.ErrorResult
import com.kakao.network.callback.ResponseCallback

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var sharedViewModel: LottoViewModel

    private val lottoRepository = LottoRepository()
    private val firebaseRepository = FirebaseRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = getViewModel(HomeViewModel::class.java, lottoRepository, firebaseRepository)
        sharedViewModel = getViewModel(LottoViewModel::class.java, activity!!, lottoRepository, firebaseRepository)

        binding.apply {
            lifecycleOwner = this@HomeFragment
            sharedViewModel = this@HomeFragment.sharedViewModel
            homeViewModel = this@HomeFragment.homeViewModel
            recyclerLatelyResult.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerLatelyResult.addItemDecoration(LatelyResultDecoration(context!!))
            adView.loadAd(AdRequest.Builder().build())
        }

        homeViewModel.apply {
            setOnItemClickListener { dto ->
                sharedViewModel.listResult.value?.let {
                    showAd(R.string.ad_id_lately_front, true) {
                        val position = it.indexOf(dto)
                        startActivity(Intent(context, LatelyResultActivity::class.java).apply {
                            putExtra(EXTRA_LIST_LOTTO, it as ArrayList<LottoDTO>)
                            putExtra(EXTRA_LATELY_RESULT_POSITION, position)
                        })
                    }
                }
            }
        }

        sharedViewModel.listResult.observe(activity!!, Observer {
            homeViewModel.setListLatelyResult(it)
        })
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.text_lately_result_more -> {
                sharedViewModel.listResult.value?.let {
                    startActivity(Intent(context, LatelyResultActivity::class.java).apply {
                        putExtra(EXTRA_LIST_LOTTO, it as ArrayList<LottoDTO>)
                    })
                }
            }
            R.id.text_store -> {
                KakaoLinkService.getInstance()
                    .sendCustom(context, getString(R.string.template_id_app), null, object : ResponseCallback<KakaoLinkResponse>() {
                        override fun onSuccess(result: KakaoLinkResponse?) {
//                            logEvent(FirebaseAnalytics.Event.SHARE, Bundle().apply {
//                                putString(FirebaseAnalytics.Param.ITEM_CATEGORY, viewModel.category.value)
//                                putString(FirebaseAnalytics.Param.ITEM_NAME, viewModel.title.value)
//                            })
                        }

                        override fun onFailure(errorResult: ErrorResult?) {
                            Log.e(TAG, "    ## ${errorResult.toString()}")
                            Toast.makeText(context, R.string.msg_unknown_error, Toast.LENGTH_SHORT).show()
                        }
                    })
//                sharedViewModel.listStore.value?.let {
//                    startActivity(Intent(context, StoreActivity::class.java).apply {
//                        putExtra(EXTRA_CURRENT_ROUND, sharedViewModel.curDrwNo.value ?: 0)
//                        putExtra(EXTRA_LIST_STORE, it as ArrayList<StoreDTO>)
//                    })
//                }
            }
        }
    }
}