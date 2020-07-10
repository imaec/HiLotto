package com.imaec.hilotto.ui.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentHomeBinding
import com.imaec.hilotto.repository.FireStoreRepository
import com.imaec.hilotto.repository.LottoRepository
import com.imaec.hilotto.utils.SharedPreferenceUtil
import com.imaec.hilotto.viewmodel.HomeViewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private lateinit var viewModel: HomeViewModel

    private val lottoRepository: LottoRepository by lazy { LottoRepository() }
    private val firestoreRepository: FireStoreRepository by lazy { FireStoreRepository() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = getViewModel(HomeViewModel::class.java, lottoRepository, firestoreRepository)

        binding.apply {
            lifecycleOwner = this@HomeFragment
            viewModel = this@HomeFragment.viewModel
            recyclerLatelyResult.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        showProgress()
        viewModel.getLotto { isSuccess ->
            hideProgress()
            if (isSuccess) {
                Toast.makeText(context, "성공", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, R.string.msg_data_fail, Toast.LENGTH_SHORT).show()
            }
        }
    }
}