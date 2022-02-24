package com.imaec.hilotto.ui.view.dialog

import android.content.Context
import android.os.Bundle
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseDialog
import com.imaec.hilotto.databinding.DialogCommonBinding

class CommonDialog(
    context: Context,
    private val message: String,
    private var positive: String = "확인",
    private var negative: String = "취소",
    private val positiveCallback: () -> Unit = {},
    private val negativeCallback: () -> Unit = {}
) : BaseDialog<DialogCommonBinding>(context, R.layout.dialog_common) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupLayout()
    }

    private fun setupLayout() {
        with(binding) {
            tvMessage.text = message
            tvOk.text = positive
            tvCancel.text = negative

            tvOk.setOnClickListener {
                dismiss()
                positiveCallback()
            }
            tvCancel.setOnClickListener {
                dismiss()
                negativeCallback()
            }
        }
    }
}
