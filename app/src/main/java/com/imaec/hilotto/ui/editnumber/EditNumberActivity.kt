package com.imaec.hilotto.ui.editnumber

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.NumberPicker
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseActivity
import com.imaec.hilotto.databinding.ActivityEditNumberBinding
import com.imaec.hilotto.model.MyNumberVo
import com.imaec.hilotto.utils.expand
import com.imaec.hilotto.utils.hide
import com.imaec.hilotto.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditNumberActivity : BaseActivity<ActivityEditNumberBinding>(R.layout.activity_edit_number) {

    private val viewModel by viewModels<EditNumberViewModel>()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupLoadingObserver(viewModel)
        setupBottomSheetBehavior()
        setupListener()
        setupObserver()
    }

    private fun setupBinding() {
        with(binding) {
            vm = viewModel
        }
    }

    private fun setupBottomSheetBehavior() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.llBottomSheet).hide()
    }

    private fun setupListener() {
        bottomSheetBehavior.addBottomSheetCallback(
            object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {}

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    viewModel.setVisibleBg(newState != BottomSheetBehavior.STATE_HIDDEN)
                }
            }
        )

        setupPickerDividerColor()
    }

    private fun setupPickerDividerColor() {
        val pickerFiled = NumberPicker::class.java.declaredFields
        for (field in pickerFiled) {
            if (field.name == "mSelectionDivider") {
                field.isAccessible = true
                try {
                    val colorDrawable = ColorDrawable(
                        ContextCompat.getColor(this, R.color.colorPrimary)
                    )
                    field.set(binding.picker, colorDrawable)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun setupObserver() {
        viewModel.state.observe(this) {
            when (it) {
                is EditNumberState.OnClickNumber -> {
                    showPicker(it.number)
                }
                EditNumberState.OnClickBg,
                EditNumberState.OnClickCancel -> {
                    bottomSheetBehavior.hide()
                }
                EditNumberState.OnClickOk -> {
                    if (!viewModel.checkNumber()) {
                        toast(R.string.msg_not_input_same_number)
                    }
                    bottomSheetBehavior.hide()
                }
                EditNumberState.OnClickFinish -> {
                    finish()
                }
                EditNumberState.OnClickSave -> {
                    viewModel.update { isSuccess ->
                        if (isSuccess) {
                            finish()
                        } else {
                            toast(R.string.msg_edit_fail)
                        }
                    }
                }
            }
        }
    }

    private fun showPicker(number: Int) {
        viewModel.setPickedNumber(number)
        val arr = Array(45) { "" }.apply {
            for (i in indices) {
                this[i] = "${i + 1}"
            }
        }
        binding.picker.apply {
            minValue = 1
            maxValue = 45
            value = number
            displayedValues = arr
            setOnValueChangedListener { _, _, newVal ->
                viewModel.setPickedNumber(newVal)
            }
        }

        bottomSheetBehavior.expand()
    }

    companion object {
        const val MY_NUMBER = "myNumber"

        fun createBundle(myNumber: MyNumberVo): Bundle = bundleOf(
            MY_NUMBER to myNumber
        )
    }
}
