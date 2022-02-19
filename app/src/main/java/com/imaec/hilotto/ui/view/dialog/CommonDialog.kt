package com.imaec.hilotto.ui.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.imaec.hilotto.R
import kotlinx.android.synthetic.main.dialog_common.*

class CommonDialog(context: Context, private val message: String) : Dialog(context) {

    private var ok = ""
    private var cancel = ""
    private lateinit var listenerOk: View.OnClickListener
    private lateinit var listenerCancel: View.OnClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_common)

        val size = Point()
        window?.windowManager?.defaultDisplay?.getSize(size)
        window?.apply {
            attributes = WindowManager.LayoutParams().apply {
                copyFrom(window?.attributes)
                width = (size.x * 0.8).toInt()
                height = WindowManager.LayoutParams.WRAP_CONTENT
            }
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        if (ok.isEmpty()) ok = context.getString(R.string.ok)
        if (cancel.isEmpty()) cancel = context.getString(R.string.cancel)

        initLayout()
    }

    private fun initLayout() {
        text_message.text = message
        text_ok.text = ok
        text_cancel.text = cancel

        if (::listenerOk.isInitialized) text_ok.setOnClickListener(listenerOk)
        else text_ok.setOnClickListener { dismiss() }

        if (::listenerCancel.isInitialized) text_cancel.setOnClickListener(listenerCancel)
        else text_cancel.setOnClickListener { dismiss() }
    }

    fun setOk(ok: String) {
        this.ok = ok
    }

    fun setCancel(cancel: String) {
        this.cancel = cancel
    }

    fun setOnOkClickListener(listener: View.OnClickListener) {
        listenerOk = listener
    }

    fun setOnCancelClickListener(listener: View.OnClickListener) {
        listenerCancel = listener
    }
}
