package com.imaec.hilotto.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentWinBinding
import com.imaec.hilotto.repository.FireStoreRepository
import com.imaec.hilotto.repository.LottoRepository
import com.imaec.hilotto.viewmodel.LottoViewModel
import com.imaec.hilotto.viewmodel.WinViewModel

class WinFragment : BaseFragment<FragmentWinBinding>(R.layout.fragment_win) {

    private lateinit var winViewModel: WinViewModel
    private lateinit var sharedViewModel: LottoViewModel

    private val lottoRepository = LottoRepository()
    private val firestoreRepository = FireStoreRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        winViewModel = getViewModel(WinViewModel::class.java)
        sharedViewModel = getViewModel(LottoViewModel::class.java, activity!!, lottoRepository, firestoreRepository)

        binding.apply {
            lifecycleOwner = this@WinFragment
            winViewModel = this@WinFragment.winViewModel
            sharedViewModel = this@WinFragment.sharedViewModel
        }

        sharedViewModel.listResult.observe(activity!!, Observer {
            winViewModel.setWinInfo(it)
        })
    }
}