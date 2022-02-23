package com.imaec.hilotto.ui.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.imaec.hilotto.R
import com.imaec.hilotto.databinding.DialogProgressBinding

class ProgressDialog(context: Context) : Dialog(context) {

    private lateinit var binding: DialogProgressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_progress, null, false)
        setContentView(binding.root)

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
