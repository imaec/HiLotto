package com.imaec.hilotto.ui.recommend

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.imaec.domain.model.MyNumberDto
import com.imaec.hilotto.BR
import com.imaec.hilotto.Lotto
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.base.BaseSingleViewAdapter
import com.imaec.hilotto.databinding.FragmentRecommendBinding
import com.imaec.hilotto.model.NumberVo
import com.imaec.hilotto.ui.view.dialog.CommonDialog
import com.imaec.hilotto.ui.main.LottoViewModel
import com.imaec.hilotto.utils.expand
import com.imaec.hilotto.utils.hide
import com.imaec.hilotto.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecommendFragment : BaseFragment<FragmentRecommendBinding>(R.layout.fragment_recommend) {

    private val viewModel by viewModels<RecommendViewModel>()
    private val lottoViewModel by activityViewModels<LottoViewModel>()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    private var pickedNumber = 1
    private var pickerFlag = 0 // 0 : 포함 수, 1 : 미포함 수

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupLoadingObserver(viewModel, lottoViewModel)
        setupLayout()
        setupListener()
        setupData()
        setupObserver()
    }

    private fun setupBinding() {
        with(binding) {
            vm = viewModel
        }
    }

    private fun setupLayout() {
        with(binding) {
            bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet).hide()

            with(rvNotIncluded) {
                val diffUtil = object : DiffUtil.ItemCallback<NumberVo>() {
                    override fun areItemsTheSame(oldItem: NumberVo, newItem: NumberVo): Boolean =
                        oldItem == newItem

                    override fun areContentsTheSame(
                        oldItem: NumberVo,
                        newItem: NumberVo
                    ): Boolean = oldItem == newItem
                }

                val animator = itemAnimator
                if (animator is SimpleItemAnimator) {
                    animator.supportsChangeAnimations = false
                }

                adapter = BaseSingleViewAdapter(
                    layoutResId = R.layout.item_number,
                    bindingItemId = BR.item,
                    viewModel = mapOf(BR.recommendVm to viewModel),
                    diffUtil = diffUtil
                )
            }
        }

        setupPickerDividerColor()
    }

    private fun setupPickerDividerColor() {
        val pickerFiled = NumberPicker::class.java.declaredFields
        for (field in pickerFiled) {
            if (field.name == "mSelectionDivider") {
                field.isAccessible = true
                try {
                    val colorDrawable = ColorDrawable(
                        ContextCompat.getColor(requireContext(), R.color.colorPrimary)
                    )
                    field.set(binding.picker, colorDrawable)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
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
    }

    private fun setupData() {
        viewModel.fetchCondition()
    }

    private fun setupObserver() {
        with(viewModel) {
            val owner = viewLifecycleOwner
            state.observe(owner) {
                when (it) {
                    RecommendState.OnClickInclude -> {
                        pickerFlag = 0
                        showPicker()
                    }
                    RecommendState.OnClickNotInclude -> {
                        if (listNotIncludeNumber.value!!.size == 6) {
                            toast(R.string.msg_not_include_number_max)
                            return@observe
                        }
                        pickerFlag = 1
                        showPicker()
                    }
                    is RecommendState.OnClickNumber -> {
                        if (it.isEmpty) {
                            pickerFlag = 0
                            showPicker()
                        } else {
                            CommonDialog(
                                context = requireContext(),
                                message = getString(R.string.msg_remove_number),
                                positiveCallback = {
                                    removeIncludeNumber(it.index)
                                }
                            ).show()
                        }
                    }
                    RecommendState.OnClickOk -> {
                        if (pickerFlag == 0) {
                            // 포함 수 추가
                            if (checkNotIncludeNumber("$pickedNumber")) {
                                setNumber()
                            } else {
                                toast(R.string.msg_exist_not_include_number)
                            }
                        } else {
                            // 미포함 수 추가
                            if (checkIncludeNumber("$pickedNumber")) {
                                addNotIncludeNumber("$pickedNumber")
                            } else {
                                toast(R.string.msg_exist_include_number)
                            }
                        }
                        bottomSheetBehavior.hide()
                    }
                    RecommendState.OnClickCancel,
                    RecommendState.OnClickBg -> {
                        bottomSheetBehavior.hide()
                    }
                    RecommendState.OnClickCreate -> {
                        if (binding.tvCreate.text == getString(R.string.create_numbers)) {
                            logEvent(CREATE_NUMBER, Bundle())
                            var index = 0
                            lottoList.value?.let { listResult ->
                                Lotto.setCount(getEmptyCount())
                                Lotto.getNumbers(
                                    listResult,
                                    listIncludeNumber.value!!,
                                    viewModel.getNotIncludeList(),
                                    binding.smCondition1.isChecked,
                                    binding.smCondition2.isChecked,
                                    binding.smCondition3.isChecked
                                ).forEach { number ->
                                    setIncludeNumber(index, "$number")
                                    index++
                                }
                            }
                        } else {
                            CommonDialog(
                                context = requireContext(),
                                message = getString(R.string.msg_remove_number),
                                positiveCallback = {
                                    (0..5).forEach { index ->
                                        setIncludeNumber(index, "")
                                    }
                                }
                            ).show()
                        }
                    }
                    RecommendState.OnClickSave -> {
                        if (getEmptyCount() > 0) {
                            toast(R.string.msg_number_not_created)
                            return@observe
                        }
                        listIncludeNumber.value?.let { list ->
                            val sortedList = list.sortedBy { it.toInt() }
                            logEvent(SAVE_NUMBER, Bundle())
                            saveNumber(
                                MyNumberDto(
                                    numberId = (
                                        sortedList[0] +
                                            sortedList[1] +
                                            sortedList[2] +
                                            sortedList[3] +
                                            sortedList[4] +
                                            sortedList[5]
                                        ).toLong(),
                                    number1 = sortedList[0].toInt(),
                                    number2 = sortedList[1].toInt(),
                                    number3 = sortedList[2].toInt(),
                                    number4 = sortedList[3].toInt(),
                                    number5 = sortedList[4].toInt(),
                                    number6 = sortedList[5].toInt()
                                )
                            ) { isSuccess ->
                                showAd(
                                    adId = R.string.ad_id_recommend_front,
                                    isRandom = true,
                                    onLoaded = {
                                        interstitialAd?.show(requireActivity())
                                    },
                                    onClosed = {
                                        if (isSuccess) {
                                            toast(R.string.msg_success_save_number)
                                            clearNumber()
                                        } else {
                                            toast(R.string.msg_numbers_is_exist)
                                        }
                                    }
                                )
                            }
                        }
                    }
                    is RecommendState.OnClickRemoveNotIncludeNumber -> {
                        CommonDialog(
                            requireContext(),
                            getString(R.string.msg_remove_number),
                            positiveCallback = {
                                removeNotIncludeNumber(it.notIncludeNumb)
                            }
                        ).show()
                    }
                }
            }
            conditionSum.observe(owner) {
                viewModel.setSumCondition(it)
            }
            conditionPick.observe(owner) {
                viewModel.setPickCondition(it)
            }
            conditionOddEven.observe(owner) {
                viewModel.setOddEvenCondition(it)
            }
            conditionAll.observe(owner) {
                viewModel.setAllCondition(it)
            }
        }

        lottoViewModel.lottoList.observe(viewLifecycleOwner) {
            viewModel.setLottoList(it)
        }
    }

    private fun showPicker() {
        pickedNumber = 1
        val arr = Array(45) { "" }.apply {
            for (i in indices) {
                this[i] = "${i + 1}"
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
                tvNumber1.text.isEmpty() -> {
                    this@RecommendFragment.viewModel.setIncludeNumber(0, "$pickedNumber")
                }
                tvNumber2.text.isEmpty() -> {
                    if (checkNumber("$pickedNumber", tvNumber1)) {
                        this@RecommendFragment.viewModel.setIncludeNumber(1, "$pickedNumber")
                    } else {
                        toast(R.string.msg_not_input_same_number)
                    }
                }
                tvNumber3.text.isEmpty() -> {
                    if (checkNumber("$pickedNumber", tvNumber1, tvNumber2)) {
                        this@RecommendFragment.viewModel.setIncludeNumber(2, "$pickedNumber")
                    } else {
                        toast(R.string.msg_not_input_same_number)
                    }
                }
                tvNumber4.text.isEmpty() -> {
                    if (checkNumber("$pickedNumber", tvNumber1, tvNumber2, tvNumber3)) {
                        this@RecommendFragment.viewModel.setIncludeNumber(3, "$pickedNumber")
                    } else {
                        toast(R.string.msg_not_input_same_number)
                    }
                }
                tvNumber5.text.isEmpty() -> {
                    if (checkNumber(
                            "$pickedNumber",
                            tvNumber1,
                            tvNumber2,
                            tvNumber3,
                            tvNumber4
                        )
                    ) {
                        this@RecommendFragment.viewModel.setIncludeNumber(4, "$pickedNumber")
                    } else {
                        toast(R.string.msg_not_input_same_number)
                    }
                }
                tvNumber6.text.isEmpty() -> {
                    if (checkNumber(
                            "$pickedNumber",
                            tvNumber1,
                            tvNumber2,
                            tvNumber3,
                            tvNumber4,
                            tvNumber5
                        )
                    ) {
                        this@RecommendFragment.viewModel.setIncludeNumber(5, "$pickedNumber")
                    } else {
                        toast(R.string.msg_not_input_same_number)
                    }
                }
            }
        }
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
            if (tvNumber1.text.isEmpty()) count++
            if (tvNumber2.text.isEmpty()) count++
            if (tvNumber3.text.isEmpty()) count++
            if (tvNumber4.text.isEmpty()) count++
            if (tvNumber5.text.isEmpty()) count++
            if (tvNumber6.text.isEmpty()) count++
        }
        return count
    }

    private fun clearNumber() {
        binding.apply {
            tvNumber1.text = ""
            tvNumber2.text = ""
            tvNumber3.text = ""
            tvNumber4.text = ""
            tvNumber5.text = ""
            tvNumber6.text = ""
        }
    }

    companion object {
        const val CREATE_NUMBER = "create_number"
        const val SAVE_NUMBER = "save_number"
    }
}
