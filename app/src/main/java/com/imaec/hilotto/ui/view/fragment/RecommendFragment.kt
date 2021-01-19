package com.imaec.hilotto.ui.view.fragment

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.switchmaterial.SwitchMaterial
import com.imaec.hilotto.Event
import com.imaec.hilotto.Lotto
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentRecommendBinding
import com.imaec.hilotto.repository.FirebaseRepository
import com.imaec.hilotto.repository.LottoRepository
import com.imaec.hilotto.repository.NumberRepository
import com.imaec.hilotto.room.AppDatabase
import com.imaec.hilotto.room.dao.NumberDao
import com.imaec.hilotto.room.entity.NumberEntity
import com.imaec.hilotto.ui.adapter.NumberAdapter
import com.imaec.hilotto.ui.util.NumberDecoration
import com.imaec.hilotto.ui.view.dialog.CommonDialog
import com.imaec.hilotto.utils.SharedPreferenceUtil
import com.imaec.hilotto.viewmodel.LottoViewModel
import com.imaec.hilotto.viewmodel.RecommendViewModel

class RecommendFragment : BaseFragment<FragmentRecommendBinding>(R.layout.fragment_recommend), CompoundButton.OnCheckedChangeListener {

    private lateinit var recommendViewModel: RecommendViewModel
    private lateinit var sharedViewModel: LottoViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var numberDao: NumberDao
    private lateinit var numberRepository: NumberRepository

    private val lottoRepository = LottoRepository()
    private val firebaseRepository = FirebaseRepository()

    private var pickedNumber = 1
    private var pickerFlag = 0 // 0 : 포함 수, 1 : 미포함 수

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        recommendViewModel = getViewModel(RecommendViewModel::class.java, numberRepository)
        sharedViewModel = getViewModel(LottoViewModel::class.java, activity!!, lottoRepository, firebaseRepository)

        binding.apply {
            lifecycleOwner = this@RecommendFragment
            recommendViewModel = this@RecommendFragment.recommendViewModel
            switchCondition1.isChecked = SharedPreferenceUtil.getBool(context!!, SharedPreferenceUtil.KEY.PREF_RECOMMEND_CONDITION_SUM, true)
            switchCondition1.setOnCheckedChangeListener(this@RecommendFragment)
            switchCondition2.isChecked = SharedPreferenceUtil.getBool(context!!, SharedPreferenceUtil.KEY.PREF_RECOMMEND_CONDITION_PICK, true)
            switchCondition2.setOnCheckedChangeListener(this@RecommendFragment)
            switchCondition3.isChecked = SharedPreferenceUtil.getBool(context!!, SharedPreferenceUtil.KEY.PREF_RECOMMEND_CONDITION_ODD_EVEN, true)
            switchCondition3.setOnCheckedChangeListener(this@RecommendFragment)
            switchConditionAll.isChecked = SharedPreferenceUtil.getBool(context!!, SharedPreferenceUtil.KEY.PREF_RECOMMEND_CONDITION_ALL, true)
            switchConditionAll.setOnCheckedChangeListener(this@RecommendFragment)
            recyclerNotIncluded.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = NumberAdapter().apply {
                    addOnClickListener {  number ->
                        if (number is String) {
                            CommonDialog(context!!, context!!.getString(R.string.msg_remove_number)).apply {
                                setOnOkClickListener(View.OnClickListener {
                                    this@RecommendFragment.recommendViewModel.removeNotIncludeNumber(number)
                                    dismiss()
                                })
                                show()
                            }
                        }
                    }
                }
                addItemDecoration(NumberDecoration(context))
            }
            bottomSheetBehavior = BottomSheetBehavior.from(linearBottomSheet).hide()
            bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {}

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    this@RecommendFragment.recommendViewModel.setVisible(newState != BottomSheetBehavior.STATE_HIDDEN)
                }
            })
        }

        sharedViewModel.listResult.observe(activity!!, {
            recommendViewModel.setListResult(it)
        })

        setDividerColor()
    }

    override fun onCheckedChanged(cp: CompoundButton, b: Boolean) {
        when(cp.id) {
            R.id.switch_condition1 -> {
                SharedPreferenceUtil.putValue(context!!, SharedPreferenceUtil.KEY.PREF_RECOMMEND_CONDITION_SUM, b)
                setCheck(b && binding.switchCondition2.isChecked
                        && binding.switchCondition3.isChecked, b, cp as SwitchMaterial)
            }
            R.id.switch_condition2 -> {
                SharedPreferenceUtil.putValue(context!!, SharedPreferenceUtil.KEY.PREF_RECOMMEND_CONDITION_PICK, b)
                setCheck(b && binding.switchCondition1.isChecked
                        && binding.switchCondition3.isChecked, b, cp as SwitchMaterial)
            }
            R.id.switch_condition3 -> {
                SharedPreferenceUtil.putValue(context!!, SharedPreferenceUtil.KEY.PREF_RECOMMEND_CONDITION_ODD_EVEN, b)
                setCheck(b && binding.switchCondition1.isChecked
                        && binding.switchCondition2.isChecked, b, cp as SwitchMaterial)
            }
            R.id.switch_condition_all -> {
                setCheck(true, b, cp as SwitchMaterial)
            }
        }
    }

    fun onClick(view: View) {
        when(view.id) {
            R.id.text_number1,
            R.id.text_number2,
            R.id.text_number3,
            R.id.text_number4,
            R.id.text_number5,
            R.id.text_number6 -> {
                if ((view as TextView).text.isEmpty()) {
                    pickerFlag = 0
                    showPicker()
                } else {
                    CommonDialog(context!!, context!!.getString(R.string.msg_remove_number)).apply {
                        setOnOkClickListener(View.OnClickListener {
                            removeNumber(view)
                            dismiss()
                        })
                        show()
                    }
                }
            }
            R.id.view_bg -> {
                bottomSheetBehavior.hide()
            }
            R.id.image_included_add -> {
                pickerFlag = 0
                showPicker()
            }
            R.id.image_not_included_add -> {
                if (recommendViewModel.listNotIncludeNumber.value!!.size == 6) {
                    Toast.makeText(context, context?.getString(R.string.msg_not_include_number_max), Toast.LENGTH_SHORT).show()
                    return
                }
                pickerFlag = 1
                showPicker()
            }
            R.id.text_include_cancel -> {
                bottomSheetBehavior.hide()
            }
            R.id.text_include_confirm -> {
                if (pickerFlag == 0) {
                    if (recommendViewModel.checkNotIncludeNumber("$pickedNumber")) setNumber()
                    else Toast.makeText(context, R.string.msg_exist_not_include_number, Toast.LENGTH_SHORT).show()
                } else {
                    if (recommendViewModel.checkIncludeNumber("$pickedNumber")) recommendViewModel.addNotIncludeNumber("$pickedNumber")
                    else Toast.makeText(context, R.string.msg_exist_include_number, Toast.LENGTH_SHORT).show()
                }
                bottomSheetBehavior.hide()
            }
            R.id.text_create -> {
                if (binding.textCreate.text == getString(R.string.create_numbers)) {
                    logEvent(Event.CREATE_NUMBER, Bundle())
                    recommendViewModel.apply {
                        var index = 0
                        listResult.value?.let { listResult ->
                            Lotto.setCount(getEmptyCount())
                            Lotto.getNumbers(
                                listResult,
                                listIncludeNumber.value!!,
                                listNotIncludeNumber.value!!,
                                binding.switchCondition1.isChecked,
                                binding.switchCondition2.isChecked,
                                binding.switchCondition3.isChecked
                            ).forEach { number ->
                                setIncludeNumber(index, "$number")
                                index++
                            }
                        }
                    }
                } else {
                    CommonDialog(context!!, context!!.getString(R.string.msg_remove_number)).apply {
                        setOnOkClickListener(View.OnClickListener {
                            (0..5).forEach {
                                recommendViewModel.setIncludeNumber(it, "")
                            }
                            dismiss()
                        })
                        show()
                    }
                }
            }
            R.id.text_save -> {
                if (getEmptyCount() > 0) {
                    Toast.makeText(context, R.string.msg_number_not_created, Toast.LENGTH_SHORT).show()
                    return
                }
                recommendViewModel.apply {
                    listIncludeNumber.value?.let { list ->
                        val sortedList = list.sortedBy { it.toInt() }
                        logEvent(Event.SAVE_NUMBER, Bundle())
                        saveNumber(NumberEntity(
                            numberId = "${sortedList[0]}${sortedList[1]}${sortedList[2]}${sortedList[3]}${sortedList[4]}${sortedList[5]}".toLong(),
                            number1 = sortedList[0].toInt(),
                            number2 = sortedList[1].toInt(),
                            number3 = sortedList[2].toInt(),
                            number4 = sortedList[3].toInt(),
                            number5 = sortedList[4].toInt(),
                            number6 = sortedList[5].toInt())
                        ) { isSuccess ->
                            showAd(R.string.ad_id_recommend_front, true) {
                                if (isSuccess) {
                                    Toast.makeText(context, R.string.msg_success_save_number, Toast.LENGTH_SHORT).show()
                                    clearNumber()
                                } else Toast.makeText(context, R.string.msg_numbers_is_exist, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
            R.id.image_setting -> {

            }
        }
    }

    private fun init() {
        numberDao = AppDatabase.getInstance(context!!).numberDao()
        numberRepository = NumberRepository(numberDao)
    }

    private fun setCheck(all: Boolean, isChecked: Boolean, switch: SwitchMaterial) {
        SharedPreferenceUtil.putValue(context!!, SharedPreferenceUtil.KEY.PREF_RECOMMEND_CONDITION_ALL, all)
        if (all) {
            if (switch.id == R.id.switch_condition_all) {
                if (!isChecked) {
                    binding.apply {
                        switchCondition1.isChecked = false
                        switchCondition2.isChecked = false
                        switchCondition3.isChecked = false
                        switchConditionAll.isChecked = false
                    }
                    return
                }
            }
            binding.apply {
                switchCondition1.isChecked = true
                switchCondition2.isChecked = true
                switchCondition3.isChecked = true
                switchConditionAll.isChecked = true
            }
        } else {
            binding.switchConditionAll.apply {
                if (this.isChecked) {
                    setOnCheckedChangeListener(null)
                    this.isChecked = false
                    setOnCheckedChangeListener(this@RecommendFragment)
                }
            }
        }
    }

    private fun setDividerColor() {
        val pickerFiled = NumberPicker::class.java.declaredFields
        for (field in pickerFiled) {
            if (field.name == "mSelectionDivider") {
                field.isAccessible = true
                try {
                    val colorDrawable = ColorDrawable(ContextCompat.getColor(context!!, R.color.colorPrimary))
                    field.set(binding.picker, colorDrawable)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun showPicker() {
        pickedNumber = 1
        val arr = Array(45) {""}.apply {
            for (i in indices) {
                this[i] = "${i+1}"
            }
        }
        binding.picker.apply {
            minValue = 1
            maxValue = 45
            value = 1
            displayedValues = arr
            setOnValueChangedListener { _, _, newVal ->
                pickedNumber = newVal
            }
        }

        bottomSheetBehavior.expand()
    }

    private fun setNumber() {
        binding.apply {
            when {
                textNumber1.text.isEmpty() -> {
                    this@RecommendFragment.recommendViewModel.setIncludeNumber(0, "$pickedNumber")
                }
                textNumber2.text.isEmpty() -> {
                    if (checkNumber("$pickedNumber", textNumber1)) {
                        this@RecommendFragment.recommendViewModel.setIncludeNumber(1, "$pickedNumber")
                    } else {
                        Toast.makeText(context, R.string.msg_not_input_same_number, Toast.LENGTH_SHORT).show()
                    }
                }
                textNumber3.text.isEmpty() -> {
                    if (checkNumber("$pickedNumber", textNumber1, textNumber2)) {
                        this@RecommendFragment.recommendViewModel.setIncludeNumber(2, "$pickedNumber")
                    } else {
                        Toast.makeText(context, R.string.msg_not_input_same_number, Toast.LENGTH_SHORT).show()
                    }
                }
                textNumber4.text.isEmpty() -> {
                    if (checkNumber("$pickedNumber", textNumber1, textNumber2, textNumber3)) {
                        this@RecommendFragment.recommendViewModel.setIncludeNumber(3, "$pickedNumber")
                    } else {
                        Toast.makeText(context, R.string.msg_not_input_same_number, Toast.LENGTH_SHORT).show()
                    }
                }
                textNumber5.text.isEmpty() -> {
                    if (checkNumber("$pickedNumber", textNumber1, textNumber2, textNumber3, textNumber4)) {
                        this@RecommendFragment.recommendViewModel.setIncludeNumber(4, "$pickedNumber")
                    } else {
                        Toast.makeText(context, R.string.msg_not_input_same_number, Toast.LENGTH_SHORT).show()
                    }
                }
                textNumber6.text.isEmpty() -> {
                    if (checkNumber("$pickedNumber", textNumber1, textNumber2, textNumber3, textNumber4, textNumber5)) {
                        this@RecommendFragment.recommendViewModel.setIncludeNumber(5, "$pickedNumber")
                    } else {
                        Toast.makeText(context, R.string.msg_not_input_same_number, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun removeNumber(textView: TextView) {
        recommendViewModel.removeIncludeNumber(
            when (textView.id) {
                R.id.text_number1 -> 0
                R.id.text_number2 -> 1
                R.id.text_number3 -> 2
                R.id.text_number4 -> 3
                R.id.text_number5 -> 4
                R.id.text_number6 -> 5
                else -> throw IllegalArgumentException("Incorrect TextView")
            }
        )
    }

    private fun checkNumber(number: String, vararg textViews: TextView): Boolean {
        textViews.forEach {
            if (it.text.toString() == number) return false
        }
        return true
    }

    private fun getEmptyCount(): Int {
        var count = 0
        binding.apply {
            if (textNumber1.text.isEmpty()) count++
            if (textNumber2.text.isEmpty()) count++
            if (textNumber3.text.isEmpty()) count++
            if (textNumber4.text.isEmpty()) count++
            if (textNumber5.text.isEmpty()) count++
            if (textNumber6.text.isEmpty()) count++
        }
        return count
    }

    private fun clearNumber() {
        binding.apply {
            textNumber1.text = ""
            textNumber2.text = ""
            textNumber3.text = ""
            textNumber4.text = ""
            textNumber5.text = ""
            textNumber6.text = ""
        }
    }

    private fun <V : View> BottomSheetBehavior<V>.expand() : BottomSheetBehavior<V> {
        state = BottomSheetBehavior.STATE_EXPANDED
        return this
    }

    private fun <V : View> BottomSheetBehavior<V>.hide() : BottomSheetBehavior<V> {
        state = BottomSheetBehavior.STATE_HIDDEN
        return this
    }
}