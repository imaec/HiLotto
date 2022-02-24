package com.imaec.hilotto.ui.view.dialog

import android.content.Context
import android.os.Bundle
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseDialog
import com.imaec.hilotto.databinding.DialogEditBinding

class EditDialog(
    context: Context,
    private val editCallback: () -> Unit,
    private val deleteCallback: () -> Unit
) : BaseDialog<DialogEditBinding>(context, R.layout.dialog_edit) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupLayout()
    }

    private fun setupLayout() {
        with(binding) {
            tvEdit.setOnClickListener {
                dismiss()
                editCallback()
            }
            tvDelete.setOnClickListener {
                dismiss()
                deleteCallback()
            }
        }
    }
}
