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
import com.imaec.hilotto.viewmodel.RecommendViewModel

class RecommendFragment : BaseFragment<FragmentRecommendBinding>(R.layout.fragment_recommend) {

    private lateinit var viewModel: RecommendViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

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

            }
            R.id.view_bg -> {
                bottomSheetBehavior.hide()
            }
            R.id.image_included_add -> {
                showPicker()
            }
            R.id.image_not_included_add -> {

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
        val arr = Array(46) {""}.apply {
            for (i in indices) {
                this[i] = if (i == 0) "삭제" else "$i"
            }
        }
        binding.picker.apply {
            minValue = 1
            maxValue = 46
            value = 2
            displayedValues = arr
            setOnValueChangedListener { _, _, newVal ->
                setNumber(newVal)
            }
        }

        bottomSheetBehavior.expand()
    }

    private fun setNumber(number: Int) {
        binding.apply {
            when {
                textNumber1.text.isEmpty() -> {
                    textNumber1.text = if (number == 1) "" else "${number-1}"
                }
                textNumber2.text.isEmpty() -> {
                    textNumber2.text = if (number == 1) "" else "${number-1}"
                }
                textNumber3.text.isEmpty() -> {
                    textNumber3.text = if (number == 1) "" else "${number-1}"
                }
                textNumber4.text.isEmpty() -> {
                    textNumber4.text = if (number == 1) "" else "${number-1}"
                }
                textNumber5.text.isEmpty() -> {
                    textNumber5.text = if (number == 1) "" else "${number-1}"
                }
                textNumber6.text.isEmpty() -> {
                    textNumber6.text = if (number == 1) "" else "${number-1}"
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