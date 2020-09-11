package com.imaec.hilotto.ui.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
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
        }

        homeViewModel.apply {
            setOnItemClickListener { dto ->
                sharedViewModel.listResult.value?.let {
                    val position = it.indexOf(dto)
                    startActivity(Intent(context, LatelyResultActivity::class.java).apply {
                        putExtra(EXTRA_LIST_LOTTO, it as ArrayList<LottoDTO>)
                        putExtra(EXTRA_LATELY_RESULT_POSITION, position)
                    })
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
                sharedViewModel.listStore.value?.let {
                    startActivity(Intent(context, StoreActivity::class.java).apply {
                        putExtra(EXTRA_CURRENT_ROUND, sharedViewModel.curDrwNo.value ?: 0)
                        putExtra(EXTRA_LIST_STORE, it as ArrayList<StoreDTO>)
                    })
                }
            }
        }
    }
}