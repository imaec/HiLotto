package com.imaec.hilotto.ui.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentHomeBinding
import com.imaec.hilotto.repository.LottoRepository
import com.imaec.hilotto.utils.SharedPreferenceUtil
import com.imaec.hilotto.viewmodel.HomeViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private lateinit var viewModel: HomeViewModel

    private val repository: LottoRepository by lazy { LottoRepository() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = getViewModel(HomeViewModel::class.java, repository)

        binding.apply {
            lifecycleOwner = this@HomeFragment
            viewModel = this@HomeFragment.viewModel
        }

        showProgress()
        val curDrwNo = SharedPreferenceUtil.getInt(context!!, SharedPreferenceUtil.KEY.PREF_WEEK, 1)
        viewModel.checkLotto(curDrwNo) {
            hideProgress()
        }
    }
}