package com.imaec.hilotto.ui.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.imaec.hilotto.R
import com.imaec.hilotto.databinding.DialogCopyBinding
import com.imaec.hilotto.utils.toast

class CopyDialog(context: Context) : Dialog(context) {

    private lateinit var binding: DialogCopyBinding

    private var title = "안내"
    private lateinit var listenerStore: View.OnClickListener
    private lateinit var listenerAddress: View.OnClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_copy, null, false)
        setContentView(binding.root)

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

        binding.textTitle.text = title

        if (::listenerStore.isInitialized) {
            binding.textCopyStore.setOnClickListener(listenerStore)
        } else {
            binding.textCopyStore.setOnClickListener {
                context.toast(R.string.msg_copy_store_click)
                dismiss()
            }
        }

        if (::listenerAddress.isInitialized) {
            binding.textCopyAddress.setOnClickListener(listenerAddress)
        } else {
            binding.textCopyAddress.setOnClickListener {
                context.toast(R.string.msg_copy_address_click)
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
