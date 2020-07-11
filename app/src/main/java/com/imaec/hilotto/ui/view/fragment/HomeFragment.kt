package com.imaec.hilotto.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentHomeBinding
import com.imaec.hilotto.repository.FireStoreRepository
import com.imaec.hilotto.repository.LottoRepository
import com.imaec.hilotto.ui.util.LatelyResultDecoration
import com.imaec.hilotto.viewmodel.HomeViewModel
import com.imaec.hilotto.viewmodel.LottoSharedViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var sharedViewModel: LottoSharedViewModel

    private val lottoRepository: LottoRepository by lazy { LottoRepository() }
    private val firestoreRepository: FireStoreRepository by lazy { FireStoreRepository() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = getViewModel(HomeViewModel::class.java, lottoRepository, firestoreRepository)
        sharedViewModel = getSharedViewModel(LottoSharedViewModel::class.java, lottoRepository, firestoreRepository)

        binding.apply {
            lifecycleOwner = this@HomeFragment
            sharedViewModel = this@HomeFragment.sharedViewModel
            homeViewModel = this@HomeFragment.homeViewModel
            recyclerLatelyResult.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recyclerLatelyResult.addItemDecoration(LatelyResultDecoration(context!!))
        }
    }
}