package com.imaec.hilotto.ui.view.fragment

import android.os.Bundle
import android.view.View
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentPickBinding
import com.imaec.hilotto.viewmodel.PickViewModel

class PickFragment : BaseFragment<FragmentPickBinding>(R.layout.fragment_pick) {

    private lateinit var viewModel: PickViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = getViewModel(PickViewModel::class.java)

        binding.apply {
            lifecycleOwner = this@PickFragment
            viewModel = this@PickFragment.viewModel
        }
    }
}