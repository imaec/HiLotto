package com.imaec.hilotto.ui.view.dialog

import android.content.Context
import android.os.Bundle
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseDialog
import com.imaec.hilotto.databinding.DialogCopyBinding

class CopyDialog(
    context: Context,
    private val copyStoreCallback: () -> Unit,
    private val copyAddressCallback: () -> Unit
) : BaseDialog<DialogCopyBinding>(context, R.layout.dialog_copy) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupLayout()
    }

    private fun setupLayout() {
        with(binding) {
            tvCopyStore.setOnClickListener {
                dismiss()
                copyStoreCallback()
            }
            tvCopyAddress.setOnClickListener {
                dismiss()
                copyAddressCallback()
            }
        }
    }
}
