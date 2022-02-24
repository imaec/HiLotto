package com.imaec.hilotto.ui.view.dialog

import android.content.Context
import android.os.Bundle
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseDialog
import com.imaec.hilotto.databinding.DialogInfoBinding

class InfoDialog(
    context: Context,
    private val message: String,
    private val okCallback: () -> Unit = {}
) : BaseDialog<DialogInfoBinding>(context, R.layout.dialog_info) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupLayout()
    }

    private fun setupLayout() {
        with(binding) {
            tvMessage.text = message

            tvOk.setOnClickListener {
                dismiss()
                okCallback()
            }
        }
    }
}
