package com.imaec.hilotto.ui.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.imaec.hilotto.R
import kotlinx.android.synthetic.main.dialog_copy.*

class CopyDialog(context: Context) : Dialog(context) {

    private var title = "안내"
    private lateinit var listenerStore: View.OnClickListener
    private lateinit var listenerAddress: View.OnClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_copy)

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

        text_title.text = title

        if (::listenerStore.isInitialized) {
            text_copy_store.setOnClickListener(listenerStore)
        } else {
            text_copy_store.setOnClickListener {
                Toast.makeText(context, R.string.msg_copy_store_click, Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }

        if (::listenerAddress.isInitialized) {
            text_copy_address.setOnClickListener(listenerAddress)
        } else {
            text_copy_address.setOnClickListener {
                Toast.makeText(context, R.string.msg_copy_address_click, Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun setOnCopyStoreClickListener(listener: View.OnClickListener) {
        listenerStore = listener
    }

    fun setOnCopyAddressClickListener(listener: View.OnClickListener) {
        listenerAddress = listener
    }
}
