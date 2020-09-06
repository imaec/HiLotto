package com.imaec.hilotto.ui.view.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.imaec.hilotto.*
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentMyBinding
import com.imaec.hilotto.model.LottoDTO
import com.imaec.hilotto.repository.FirebaseRepository
import com.imaec.hilotto.repository.LottoRepository
import com.imaec.hilotto.repository.NumberRepository
import com.imaec.hilotto.room.AppDatabase
import com.imaec.hilotto.room.dao.NumberDao
import com.imaec.hilotto.room.entity.NumberEntity
import com.imaec.hilotto.ui.util.NumbersDecoration
import com.imaec.hilotto.ui.view.activity.EditNumberActivity
import com.imaec.hilotto.ui.view.activity.WinHistoryActivity
import com.imaec.hilotto.ui.view.dialog.CommonDialog
import com.imaec.hilotto.ui.view.dialog.EditDialog
import com.imaec.hilotto.viewmodel.LottoViewModel
import com.imaec.hilotto.viewmodel.MyViewModel

class MyFragment : BaseFragment<FragmentMyBinding>(R.layout.fragment_my) {

    private lateinit var myViewModel: MyViewModel
    private lateinit var sharedViewModel: LottoViewModel
    private lateinit var numberDao: NumberDao
    private lateinit var numberRepository: NumberRepository

    private val lottoRepository = LottoRepository()
    private val firebaseRepository = FirebaseRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        myViewModel = getViewModel(MyViewModel::class.java, numberRepository)
        sharedViewModel = getViewModel(LottoViewModel::class.java, activity!!, lottoRepository, firebaseRepository)

        binding.apply {
            lifecycleOwner = this@MyFragment
            myViewModel = this@MyFragment.myViewModel
            recyclerMyNumbers.layoutManager = LinearLayoutManager(context)
            recyclerMyNumbers.addItemDecoration(NumbersDecoration(context!!, 6, 12))
        }

        myViewModel.apply {
            listNumber.observe(activity!!, Observer {
                sharedViewModel.listResult.value?.let {
                    checkWin(it[it.size-1])
                }
            })
            setOnNumberClickListener { entity ->
                if (entity !is NumberEntity) {
                    Toast.makeText(context, R.string.msg_unknown_error, Toast.LENGTH_SHORT).show()
                    return@setOnNumberClickListener
                }
                sharedViewModel.listResult.value?.let {
                    val listLotto = ArrayList<LottoDTO>().apply {
                        addAll(it)
                    }
                    startActivity(Intent(context, WinHistoryActivity::class.java).apply {
                        putExtra(EXTRA_LIST_LOTTO, listLotto)
                        putExtra(EXTRA_MY_NUMBER, entity)
                    })
                }
            }
            setOnNumberLongClickListener { entity ->
                if (entity !is NumberEntity) {
                    Toast.makeText(context, R.string.msg_unknown_error, Toast.LENGTH_SHORT).show()
                    return@setOnNumberLongClickListener
                }
                EditDialog(context!!).apply {
                    setTitle(context.getString(R.string.edit))
                    setOnEditClickListener(View.OnClickListener {
                        dismiss()
                        startActivityForResult(Intent(context, EditNumberActivity::class.java).apply {
                            putExtra(EXTRA_NUMBER_ID, entity.numberId)
                            putExtra(EXTRA_NUMBER_1, entity.number1)
                            putExtra(EXTRA_NUMBER_2, entity.number2)
                            putExtra(EXTRA_NUMBER_3, entity.number3)
                            putExtra(EXTRA_NUMBER_4, entity.number4)
                            putExtra(EXTRA_NUMBER_5, entity.number5)
                            putExtra(EXTRA_NUMBER_6, entity.number6)
                        }, REQUEST_EDIT_NUMBER)
                    })
                    setOnDeleteClickListener(View.OnClickListener {
                        dismiss()
                        CommonDialog(context, context.getString(R.string.msg_remove_number)).apply {
                            setOnOkClickListener(View.OnClickListener {
                                myViewModel.deleteNumber(entity)
                                dismiss()
                            })
                            show()
                        }
                    })
                    show()
                }
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        if (!hidden) {
            myViewModel.getNumbers()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != RESULT_OK) return

        if (requestCode == REQUEST_EDIT_NUMBER) {
            myViewModel.getNumbers()
        }
    }

    private fun init() {
        numberDao = AppDatabase.getInstance(context!!).numberDao()
        numberRepository = NumberRepository(numberDao)
    }
}