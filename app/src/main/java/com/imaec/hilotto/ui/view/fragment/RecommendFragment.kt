package com.imaec.hilotto.ui.view.fragment

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentRecommendBinding
import com.imaec.hilotto.ui.adapter.NumberAdapter
import com.imaec.hilotto.ui.util.NumberDecoration
import com.imaec.hilotto.ui.view.dialog.CommonDialog
import com.imaec.hilotto.viewmodel.RecommendViewModel

class RecommendFragment : BaseFragment<FragmentRecommendBinding>(R.layout.fragment_recommend), CompoundButton.OnCheckedChangeListener {

    private lateinit var viewModel: RecommendViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    private var pickedNumber = 1
    private var pickerFlag = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = getViewModel(RecommendViewModel::class.java)

        binding.apply {
            lifecycleOwner = this@RecommendFragment
            viewModel = this@RecommendFragment.viewModel
            switchCondition1.setOnCheckedChangeListener(this@RecommendFragment)
            switchCondition2.setOnCheckedChangeListener(this@RecommendFragment)
            switchCondition3.setOnCheckedChangeListener(this@RecommendFragment)
            switchCondition4.setOnCheckedChangeListener(this@RecommendFragment)
            switchConditionAll.setOnCheckedChangeListener(this@RecommendFragment)
            recyclerNotIncluded.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = NumberAdapter().apply {
                    addOnClickListener {  number ->
                        if (number is String) {
                            CommonDialog(context!!, context!!.getString(R.string.msg_remove_number)).apply {
                                setOnOkClickListener(View.OnClickListener {
                                    this@RecommendFragment.viewModel.removeNotIncludeNumber(number)
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
                    this@RecommendFragment.viewModel.setVisible(newState != BottomSheetBehavior.STATE_HIDDEN)
                }
            })
        }

        setDividerColor()
    }

    override fun onCheckedChanged(cp: CompoundButton, b: Boolean) {
        Log.d(TAG, "    ## $cp / $b")
        when(cp.id) {
            R.id.switch_condition1 -> {
                setCheck(b && binding.switchCondition2.isChecked
                        && binding.switchCondition3.isChecked
                        && binding.switchCondition4.isChecked, b, cp as Switch)
            }
            R.id.switch_condition2 -> {
                setCheck(b && binding.switchCondition1.isChecked
                        && binding.switchCondition3.isChecked
                        && binding.switchCondition4.isChecked, b, cp as Switch)
            }
            R.id.switch_condition3 -> {
                setCheck(b && binding.switchCondition1.isChecked
                        && binding.switchCondition2.isChecked
                        && binding.switchCondition4.isChecked, b, cp as Switch)
            }
            R.id.switch_condition4 -> {
                setCheck(b && binding.switchCondition1.isChecked
                        && binding.switchCondition2.isChecked
                        && binding.switchCondition3.isChecked, b, cp as Switch)
            }
            R.id.switch_condition_all -> {
                setCheck(true, b, cp as Switch)
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
                if ((view as TextView).text.isEmpty()) return
                CommonDialog(context!!, context!!.getString(R.string.msg_remove_number)).apply {
                    setOnOkClickListener(View.OnClickListener {
                        removeNumber(view)
                        dismiss()
                    })
                    show()
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
                if (viewModel.listNotIncludeNumber.value!!.size == 6) {
                    Toast.makeText(context, context?.getString(R.string.msg_not_include_number_max), Toast.LENGTH_SHORT).show()
                    return
                }
                pickerFlag = 1
                showPicker()
            }
            R.id.text_include_cancel -> {
                pickedNumber = 1
                bottomSheetBehavior.hide()
            }
            R.id.text_include_confirm -> {
                if (pickerFlag == 0) {
                    setNumber()
                } else {
                    viewModel.addNotIncludeNumber("$pickedNumber")
                }
                bottomSheetBehavior.hide()
            }
        }
    }

    private fun setCheck(all: Boolean, isChecked: Boolean, switch: Switch) {
        Log.d(TAG, "    ## $all / $isChecked / $switch")
        if (all) {
            if (switch.id == R.id.switch_condition_all) {
                if (!isChecked) {
                    binding.apply {
                        switchCondition1.isChecked = false
                        switchCondition2.isChecked = false
                        switchCondition3.isChecked = false
                        switchCondition4.isChecked = false
                        switchConditionAll.isChecked = false
                    }
                    return
                }
            }
            binding.apply {
                switchCondition1.isChecked = true
                switchCondition2.isChecked = true
                switchCondition3.isChecked = true
                switchCondition4.isChecked = true
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
                    val colorDrawable = ColorDrawable(resources.getColor(R.color.colorPrimary))
                    field.set(binding.picker, colorDrawable)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun showPicker() {
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
                    this@RecommendFragment.viewModel.setIncludeNumber(0, "$pickedNumber")
                }
                textNumber2.text.isEmpty() -> {
                    if (checkNumber("$pickedNumber", textNumber1)) {
                        this@RecommendFragment.viewModel.setIncludeNumber(1, "$pickedNumber")
                    } else {
                        Toast.makeText(context, "같은 숫자를 입력할 수 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                textNumber3.text.isEmpty() -> {
                    if (checkNumber("$pickedNumber", textNumber1, textNumber2)) {
                        this@RecommendFragment.viewModel.setIncludeNumber(2, "$pickedNumber")
                    } else {
                        Toast.makeText(context, "같은 숫자를 입력할 수 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                textNumber4.text.isEmpty() -> {
                    if (checkNumber("$pickedNumber", textNumber1, textNumber2, textNumber3)) {
                        this@RecommendFragment.viewModel.setIncludeNumber(3, "$pickedNumber")
                    } else {
                        Toast.makeText(context, "같은 숫자를 입력할 수 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                textNumber5.text.isEmpty() -> {
                    if (checkNumber("$pickedNumber", textNumber1, textNumber2, textNumber3, textNumber4)) {
                        this@RecommendFragment.viewModel.setIncludeNumber(4, "$pickedNumber")
                    } else {
                        Toast.makeText(context, "같은 숫자를 입력할 수 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                textNumber6.text.isEmpty() -> {
                    if (checkNumber("$pickedNumber", textNumber1, textNumber2, textNumber3, textNumber4, textNumber5)) {
                        this@RecommendFragment.viewModel.setIncludeNumber(5, "$pickedNumber")
                    } else {
                        Toast.makeText(context, "같은 숫자를 입력할 수 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun removeNumber(textView: TextView) {
        viewModel.removeIncludeNumber(
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

    private fun <V : View> BottomSheetBehavior<V>.expand() : BottomSheetBehavior<V> {
        state = BottomSheetBehavior.STATE_EXPANDED
        return this
    }

    private fun <V : View> BottomSheetBehavior<V>.hide() : BottomSheetBehavior<V> {
        state = BottomSheetBehavior.STATE_HIDDEN
        return this
    }
}