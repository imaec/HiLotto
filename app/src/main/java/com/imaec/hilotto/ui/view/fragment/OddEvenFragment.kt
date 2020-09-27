package com.imaec.hilotto.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentOddEvenBinding
import com.imaec.hilotto.repository.FirebaseRepository
import com.imaec.hilotto.repository.LottoRepository
import com.imaec.hilotto.ui.util.NumbersDecoration
import com.imaec.hilotto.utils.SharedPreferenceUtil
import com.imaec.hilotto.viewmodel.LottoViewModel
import com.imaec.hilotto.viewmodel.OddEvenViewModel

class OddEvenFragment : BaseFragment<FragmentOddEvenBinding>(R.layout.fragment_odd_even) {

    private lateinit var oddEvenViewModel: OddEvenViewModel
    private lateinit var sharedViewModel: LottoViewModel

    private val lottoRepository = LottoRepository()
    private val firebaseRepository = FirebaseRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        oddEvenViewModel = getViewModel(OddEvenViewModel::class.java)
        sharedViewModel = getViewModel(LottoViewModel::class.java, activity!!, lottoRepository, firebaseRepository)

        binding.apply {
            lifecycleOwner = this@OddEvenFragment
            oddEvenViewModel = this@OddEvenFragment.oddEvenViewModel
            sharedViewModel = this@OddEvenFragment.sharedViewModel
            recyclerOddEven.layoutManager = LinearLayoutManager(context)
            recyclerOddEven.addItemDecoration(NumbersDecoration(context!!))
        }

        oddEvenViewModel.setStatisticsNo(SharedPreferenceUtil.getInt(context!!, SharedPreferenceUtil.KEY.PREF_SETTING_STATISTICS, 20))

        sharedViewModel.listResult.observe(activity!!, Observer {
            oddEvenViewModel.setOddEven(it)
        })
    }
}