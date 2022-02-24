package com.imaec.hilotto.ui.view.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseDialog
import com.imaec.hilotto.databinding.DialogProgressBinding

class ProgressDialog(
    context: Context
) : BaseDialog<DialogProgressBinding>(context, R.layout.dialog_progress) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.apply {
            attributes = WindowManager.LayoutParams().apply {
                copyFrom(window?.attributes)
                width = WindowManager.LayoutParams.MATCH_PARENT
                height = WindowManager.LayoutParams.MATCH_PARENT
            }
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        setCancelable(false)
    }
}
