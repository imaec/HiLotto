package com.imaec.hilotto.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentSumBinding
import com.imaec.hilotto.repository.FirebaseRepository
import com.imaec.hilotto.repository.LottoRepository
import com.imaec.hilotto.ui.util.SumDecoration
import com.imaec.hilotto.viewmodel.LottoViewModel
import com.imaec.hilotto.viewmodel.SumViewModel

class SumFragment : BaseFragment<FragmentSumBinding>(R.layout.fragment_sum) {

    private lateinit var sumViewModel: SumViewModel
    private lateinit var sharedViewModel: LottoViewModel

    private val lottoRepository = LottoRepository()
    private val firebaseRepository = FirebaseRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sumViewModel = getViewModel(SumViewModel::class.java)
        sharedViewModel = getViewModel(LottoViewModel::class.java, activity!!, lottoRepository, firebaseRepository)

        binding.apply {
            lifecycleOwner = this@SumFragment
            sumViewModel = this@SumFragment.sumViewModel
            sharedViewModel = this@SumFragment.sharedViewModel
            recyclerSum.layoutManager = LinearLayoutManager(context)
            recyclerSum.addItemDecoration(SumDecoration(context!!))
        }

        sharedViewModel.listResult.observe(activity!!, Observer {
            sumViewModel.setListSum(it)
            binding.checkIncludeBonus.setOnCheckedChangeListener { compoundButton, b ->
                sumViewModel.setListSum(it, b)
            }
        })
    }
}