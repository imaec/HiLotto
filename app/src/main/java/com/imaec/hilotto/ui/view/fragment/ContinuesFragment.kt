package com.imaec.hilotto.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentContinuesBinding
import com.imaec.hilotto.repository.FireStoreRepository
import com.imaec.hilotto.repository.LottoRepository
import com.imaec.hilotto.ui.util.NumbersDecoration
import com.imaec.hilotto.viewmodel.ContinuesViewModel
import com.imaec.hilotto.viewmodel.LottoViewModel

class ContinuesFragment : BaseFragment<FragmentContinuesBinding>(R.layout.fragment_continues) {

    private lateinit var continuesViewModel: ContinuesViewModel
    private lateinit var sharedViewModel: LottoViewModel

    private val lottoRepository = LottoRepository()
    private val firestoreRepository = FireStoreRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        continuesViewModel = getViewModel(ContinuesViewModel::class.java)
        sharedViewModel = getViewModel(LottoViewModel::class.java, activity!!, lottoRepository, firestoreRepository)

        binding.apply {
            lifecycleOwner = this@ContinuesFragment
            continuesViewModel = this@ContinuesFragment.continuesViewModel
            sharedViewModel = this@ContinuesFragment.sharedViewModel
            recyclerContinues.layoutManager = LinearLayoutManager(context)
            recyclerContinues.addItemDecoration(NumbersDecoration(context!!))
        }

        sharedViewModel.listResult.observe(activity!!, Observer {
            continuesViewModel.setPickedNum(it)
        })
    }
}