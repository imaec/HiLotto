package com.imaec.hilotto.ui.view.fragment

import android.os.Bundle
import android.view.View
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentSumBinding
import com.imaec.hilotto.databinding.FragmentWinBinding
import com.imaec.hilotto.viewmodel.SumViewModel
import com.imaec.hilotto.viewmodel.WinViewModel

class WinFragment : BaseFragment<FragmentWinBinding>(R.layout.fragment_win) {

    private lateinit var viewModel: WinViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = getViewModel(WinViewModel::class.java)

        binding.apply {
            lifecycleOwner = this@WinFragment
            viewModel = this@WinFragment.viewModel
        }
    }
}