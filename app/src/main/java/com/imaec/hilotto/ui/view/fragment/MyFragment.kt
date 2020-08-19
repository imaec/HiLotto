package com.imaec.hilotto.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentMyBinding
import com.imaec.hilotto.repository.FireStoreRepository
import com.imaec.hilotto.repository.LottoRepository
import com.imaec.hilotto.repository.NumberRepository
import com.imaec.hilotto.room.AppDatabase
import com.imaec.hilotto.room.dao.NumberDao
import com.imaec.hilotto.ui.util.NumbersDecoration
import com.imaec.hilotto.viewmodel.LottoViewModel
import com.imaec.hilotto.viewmodel.MyViewModel

class MyFragment : BaseFragment<FragmentMyBinding>(R.layout.fragment_my) {

    private lateinit var myViewModel: MyViewModel
    private lateinit var sharedViewModel: LottoViewModel
    private lateinit var numberDao: NumberDao
    private lateinit var numberRepository: NumberRepository

    private val lottoRepository = LottoRepository()
    private val firestoreRepository = FireStoreRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        myViewModel = getViewModel(MyViewModel::class.java, numberRepository)
        sharedViewModel = getViewModel(LottoViewModel::class.java, activity!!, lottoRepository, firestoreRepository)

        binding.apply {
            lifecycleOwner = this@MyFragment
            myViewModel = this@MyFragment.myViewModel
            recyclerMyNumbers.layoutManager = LinearLayoutManager(context)
            recyclerMyNumbers.addItemDecoration(NumbersDecoration(context!!, 6, 12))
        }

        myViewModel.getNumbers()
    }

    private fun init() {
        numberDao = AppDatabase.getInstance(context!!).numberDao()
        numberRepository = NumberRepository(numberDao)
    }
}