package com.imaec.hilotto.ui.view.fragment

import android.os.Bundle
import android.view.View
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentContinuesBinding
import com.imaec.hilotto.viewmodel.ContinuesViewModel

class ContinuesFragment : BaseFragment<FragmentContinuesBinding>(R.layout.fragment_continues) {

    private lateinit var viewModel: ContinuesViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = getViewModel(ContinuesViewModel::class.java)

        binding.apply {
            lifecycleOwner = this@ContinuesFragment
            viewModel = this@ContinuesFragment.viewModel
        }
    }
}