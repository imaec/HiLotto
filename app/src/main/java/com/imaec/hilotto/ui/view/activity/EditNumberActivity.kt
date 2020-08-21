package com.imaec.hilotto.ui.view.activity

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.imaec.hilotto.*
import com.imaec.hilotto.base.BaseActivity
import com.imaec.hilotto.databinding.ActivityEditNumberBinding
import com.imaec.hilotto.repository.NumberRepository
import com.imaec.hilotto.room.AppDatabase
import com.imaec.hilotto.room.dao.NumberDao
import com.imaec.hilotto.room.entity.NumberEntity
import com.imaec.hilotto.viewmodel.EditNumberViewModel

class EditNumberActivity : BaseActivity<ActivityEditNumberBinding>(R.layout.activity_edit_number) {

    private lateinit var editNumberViewModel: EditNumberViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var numberDao: NumberDao
    private lateinit var numberRepository: NumberRepository

    private lateinit var selectedTextView: TextView

    private var pickedNumber = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()

        editNumberViewModel = getViewModel(EditNumberViewModel::class.java, numberRepository)

        binding.apply {
            lifecycleOwner = this@EditNumberActivity
            editNumberViewModel = this@EditNumberActivity.editNumberViewModel
            bottomSheetBehavior = BottomSheetBehavior.from(linearBottomSheet).hide()
            bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {}

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    this@EditNumberActivity.editNumberViewModel.setVisible(newState != BottomSheetBehavior.STATE_HIDDEN)
                }
            })
        }

        editNumberViewModel.apply {
            setNumber(NumberEntity(
                intent.getIntExtra(EXTRA_NUMBER_ID, 0),
                intent.getIntExtra(EXTRA_NUMBER_1, 0),
                intent.getIntExtra(EXTRA_NUMBER_2, 0),
                intent.getIntExtra(EXTRA_NUMBER_3, 0),
                intent.getIntExtra(EXTRA_NUMBER_4, 0),
                intent.getIntExtra(EXTRA_NUMBER_5, 0),
                intent.getIntExtra(EXTRA_NUMBER_6, 0)
            ))
        }

        setDividerColor()
    }

    private fun init() {
        numberDao = AppDatabase.getInstance(this).numberDao()
        numberRepository = NumberRepository(numberDao)
    }

    fun onClick(view: View) {
        when(view.id) {
            R.id.text_number1,
            R.id.text_number2,
            R.id.text_number3,
            R.id.text_number4,
            R.id.text_number5,
            R.id.text_number6 -> {
                (view as TextView).apply {
                    selectedTextView = this
                    showPicker(text.toString().toInt())
                }
            }
            R.id.view_bg,
            R.id.text_edit_cancel -> {
                bottomSheetBehavior.hide()
            }
            R.id.text_edit_confirm -> {
                setNumber()
                bottomSheetBehavior.hide()
            }
            R.id.text_cancel -> {
                setResult(RESULT_CANCELED)
                finish()
            }
            R.id.text_save -> {
                editNumberViewModel.update { isSuccess ->
                    if (isSuccess) {
                        setResult(RESULT_OK)
                        finish()
                    } else {
                        Toast.makeText(this, R.string.msg_edit_fail, Toast.LENGTH_SHORT).show()
                    }
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

    private fun showPicker(number: Int) {
        pickedNumber = number
        val arr = Array(45) {""}.apply {
            for (i in indices) {
                this[i] = "${i+1}"
            }
        }
        binding.picker.apply {
            minValue = 1
            maxValue = 45
            value = number
            displayedValues = arr
            setOnValueChangedListener { _, _, newVal ->
                pickedNumber = newVal
            }
        }

        bottomSheetBehavior.expand()
    }

    private fun setNumber() {
        binding.apply {
            if (checkNumber("$pickedNumber", textNumber1, textNumber2, textNumber3, textNumber4, textNumber5, textNumber6)) {
                this@EditNumberActivity.editNumberViewModel.apply {
                    setNumber(NumberEntity(
                        numberEntity.value!!.numberId,
                        if (selectedTextView.id == R.id.text_number1) pickedNumber else numberEntity.value!!.number1,
                        if (selectedTextView.id == R.id.text_number2) pickedNumber else numberEntity.value!!.number2,
                        if (selectedTextView.id == R.id.text_number3) pickedNumber else numberEntity.value!!.number3,
                        if (selectedTextView.id == R.id.text_number4) pickedNumber else numberEntity.value!!.number4,
                        if (selectedTextView.id == R.id.text_number5) pickedNumber else numberEntity.value!!.number5,
                        if (selectedTextView.id == R.id.text_number6) pickedNumber else numberEntity.value!!.number6
                    ))
                }
            } else {
                Toast.makeText(this@EditNumberActivity, R.string.msg_not_input_same_number, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkNumber(number: String, vararg textViews: TextView): Boolean {
        textViews.forEach {
            if (selectedTextView.id != it.id) {
                if (it.text.toString() == number) return false
            }
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