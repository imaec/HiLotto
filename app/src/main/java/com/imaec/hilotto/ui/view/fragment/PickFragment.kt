package com.imaec.hilotto.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentPickBinding
import com.imaec.hilotto.repository.FirebaseRepository
import com.imaec.hilotto.repository.LottoRepository
import com.imaec.hilotto.ui.util.NumberDecoration
import com.imaec.hilotto.utils.SharedPreferenceUtil
import com.imaec.hilotto.viewmodel.LottoViewModel
import com.imaec.hilotto.viewmodel.PickViewModel

class PickFragment : BaseFragment<FragmentPickBinding>(R.layout.fragment_pick) {

    private lateinit var pickViewModel: PickViewModel
    private lateinit var sharedViewModel: LottoViewModel

    private val lottoRepository = LottoRepository()
    private val firebaseRepository = FirebaseRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pickViewModel = getViewModel(PickViewModel::class.java)
        sharedViewModel = getViewModel(LottoViewModel::class.java, activity!!, lottoRepository, firebaseRepository)

        binding.apply {
            lifecycleOwner = this@PickFragment
            pickViewModel = this@PickFragment.pickViewModel
            sharedViewModel = this@PickFragment.sharedViewModel
            recyclerNopick1.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(NumberDecoration(context))
            }
            recyclerNopick2.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(NumberDecoration(context))
            }
            recyclerNopick3.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(NumberDecoration(context))
            }
            recyclerNopick4.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(NumberDecoration(context))
            }
            recyclerNopick5.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(NumberDecoration(context))
            }
        }

        pickViewModel.setStatisticsNo(SharedPreferenceUtil.getInt(context!!, SharedPreferenceUtil.KEY.PREF_SETTING_STATISTICS, 20))

        sharedViewModel.listResult.observe(activity!!, Observer {
            pickViewModel.setPickedNum(it)
            pickViewModel.setNoPickNum(it)
            binding.checkIncludeBonus.setOnCheckedChangeListener { compoundButton, b ->
                pickViewModel.setPickedNum(it, b)
                pickViewModel.setNoPickNum(it, b)
            }
        })
    }
}