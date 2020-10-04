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

class InfoDialog(context: Context, private val message: String) : Dialog(context) {

    private var ok = ""
    private lateinit var listenerOk: View.OnClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_info)

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

        initLayout()
    }

    private fun initLayout() {
        text_message.text = message
        text_ok.text = ok

        if (::listenerOk.isInitialized) text_ok.setOnClickListener(listenerOk)
        else text_ok.setOnClickListener { dismiss() }
    }

    fun setOk(ok: String) {
        this.ok = ok
    }

    fun setOnOkClickListener(listener: View.OnClickListener) {
        listenerOk = listener
    }
}