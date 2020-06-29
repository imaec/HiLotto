package com.imaec.hilotto.ui.view.fragment

import android.os.Bundle
import android.view.View
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentOddEvenBinding
import com.imaec.hilotto.viewmodel.OddEvenViewModel

class OddEvenFragment : BaseFragment<FragmentOddEvenBinding>(R.layout.fragment_odd_even) {

    private lateinit var viewModel: OddEvenViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = getViewModel(OddEvenViewModel::class.java)

        binding.apply {
            lifecycleOwner = this@OddEvenFragment
            viewModel = this@OddEvenFragment.viewModel
        }
    }
}