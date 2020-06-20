package com.imaec.hilotto.ui.view.fragment

import android.os.Bundle
import android.view.View
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentMyBinding
import com.imaec.hilotto.viewmodel.MyViewModel

class MyFragment : BaseFragment<FragmentMyBinding>(R.layout.fragment_my) {

    private lateinit var viewModel: MyViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = getViewModel(MyViewModel::class.java)

        binding.apply {
            lifecycleOwner = this@MyFragment
            viewModel = this@MyFragment.viewModel
        }
    }
}