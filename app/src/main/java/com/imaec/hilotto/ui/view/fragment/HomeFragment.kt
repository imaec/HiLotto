package com.imaec.hilotto.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentHomeBinding
import com.imaec.hilotto.repository.FirebaseRepository
import com.imaec.hilotto.repository.LottoRepository
import com.imaec.hilotto.ui.util.LatelyResultDecoration
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

        sharedViewModel.listResult.observe(activity!!, Observer {
            homeViewModel.setListLatelyResult(it)
        })
    }
}