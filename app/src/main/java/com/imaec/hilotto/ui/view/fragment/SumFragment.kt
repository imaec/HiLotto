package com.imaec.hilotto.ui.view.fragment

import android.os.Bundle
import android.view.View
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentSumBinding
import com.imaec.hilotto.viewmodel.SumViewModel

class SumFragment : BaseFragment<FragmentSumBinding>(R.layout.fragment_sum) {

    private lateinit var viewModel: SumViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = getViewModel(SumViewModel::class.java)

        binding.apply {
            lifecycleOwner = this@SumFragment
            viewModel = this@SumFragment.viewModel
        }
    }
}