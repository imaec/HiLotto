package com.imaec.hilotto.ui.view.fragment

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentRecommendBinding
import com.imaec.hilotto.ui.view.dialog.CommonDialog
import com.imaec.hilotto.viewmodel.RecommendViewModel

class RecommendFragment : BaseFragment<FragmentRecommendBinding>(R.layout.fragment_recommend) {

    private lateinit var viewModel: RecommendViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    private var includeNumber = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = getViewModel(RecommendViewModel::class.java)

        binding.apply {
            lifecycleOwner = this@RecommendFragment
            viewModel = this@RecommendFragment.viewModel
            bottomSheetBehavior = BottomSheetBehavior.from(linearBottomSheet).hide()
            bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {}

                override fun onStateChanged(bottomSheet: View, newState: Int) {
//                    viewModel.isVisible.value = newState != BottomSheetBehavior.STATE_HIDDEN
                    binding.viewBg.visibility = if (newState != BottomSheetBehavior.STATE_HIDDEN) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }

//                    if (newState == BottomSheetBehavior.STATE_EXPANDED) {
//                        KeyboardUtil.hideKeyboardFrom(this@InputActivity)
//                    }
                }
            })
        }

        setDividerColor()
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.text_number1,
            R.id.text_number2,
            R.id.text_number3,
            R.id.text_number4,
            R.id.text_number5,
            R.id.text_number6 -> {
                if ((view as TextView).text.isEmpty()) return
                CommonDialog(context!!, "번호를 삭제 하시겠습니까?").apply {
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
                showPicker()
            }
            R.id.image_not_included_add -> {

            }
            R.id.text_include_cancel -> {
                includeNumber = 1
                bottomSheetBehavior.hide()
            }
            R.id.text_include_confirm -> {
                setNumber()
                bottomSheetBehavior.hide()
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
                includeNumber = newVal
            }
        }

        bottomSheetBehavior.expand()
    }

    private fun setNumber() {
        binding.apply {
            when {
                textNumber1.text.isEmpty() -> {
                    this@RecommendFragment.viewModel.setIncludeNumber(0, "$includeNumber")
                }
                textNumber2.text.isEmpty() -> {
                    if (checkNumber("$includeNumber", textNumber1)) {
                        this@RecommendFragment.viewModel.setIncludeNumber(1, "$includeNumber")
                    } else {
                        Toast.makeText(context, "같은 숫자를 입력할 수 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                textNumber3.text.isEmpty() -> {
                    if (checkNumber("$includeNumber", textNumber1, textNumber2)) {
                        this@RecommendFragment.viewModel.setIncludeNumber(2, "$includeNumber")
                    } else {
                        Toast.makeText(context, "같은 숫자를 입력할 수 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                textNumber4.text.isEmpty() -> {
                    if (checkNumber("$includeNumber", textNumber1, textNumber2, textNumber3)) {
                        this@RecommendFragment.viewModel.setIncludeNumber(3, "$includeNumber")
                    } else {
                        Toast.makeText(context, "같은 숫자를 입력할 수 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                textNumber5.text.isEmpty() -> {
                    if (checkNumber("$includeNumber", textNumber1, textNumber2, textNumber3, textNumber4)) {
                        this@RecommendFragment.viewModel.setIncludeNumber(4, "$includeNumber")
                    } else {
                        Toast.makeText(context, "같은 숫자를 입력할 수 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                textNumber6.text.isEmpty() -> {
                    if (checkNumber("$includeNumber", textNumber1, textNumber2, textNumber3, textNumber4, textNumber5)) {
                        this@RecommendFragment.viewModel.setIncludeNumber(5, "$includeNumber")
                    } else {
                        Toast.makeText(context, "같은 숫자를 입력할 수 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun removeNumber(textView: TextView) {
        viewModel.removeNumber(
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