package com.imaec.hilotto.ui.view.fragment

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.TextView
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
                includeNumber = 0
                bottomSheetBehavior.hide()
            }
            R.id.text_include_confirm -> {
                if (includeNumber == 0) removeNumber()
                else setNumber()
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
                    textNumber1.text = "$includeNumber"
                }
                textNumber2.text.isEmpty() -> {
                    textNumber2.text = "$includeNumber"
                }
                textNumber3.text.isEmpty() -> {
                    textNumber3.text = "$includeNumber"
                }
                textNumber4.text.isEmpty() -> {
                    textNumber4.text = "$includeNumber"
                }
                textNumber5.text.isEmpty() -> {
                    textNumber5.text = "$includeNumber"
                }
                textNumber6.text.isEmpty() -> {
                    textNumber6.text = "$includeNumber"
                }
            }
        }
    }

    private fun removeNumber() {
        binding.apply {
            when {
                textNumber6.text.isNotEmpty() -> {
                    textNumber6.text = ""
                }
                textNumber5.text.isNotEmpty() -> {
                    textNumber5.text = ""
                }
                textNumber4.text.isNotEmpty() -> {
                    textNumber4.text = ""
                }
                textNumber3.text.isNotEmpty() -> {
                    textNumber3.text = ""
                }
                textNumber2.text.isNotEmpty() -> {
                    textNumber2.text = ""
                }
                textNumber1.text.isNotEmpty() -> {
                    textNumber1.text = ""
                }
            }
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