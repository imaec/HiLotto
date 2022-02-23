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
import com.imaec.hilotto.databinding.DialogEditBinding
import com.imaec.hilotto.utils.toast

class EditDialog(context: Context) : Dialog(context) {

    private lateinit var binding: DialogEditBinding

    private var title = "안내"
    private lateinit var listenerEdit: View.OnClickListener
    private lateinit var listenerDelete: View.OnClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_edit, null, false)
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

        if (::listenerEdit.isInitialized) {
            binding.textEdit.setOnClickListener(listenerEdit)
        } else {
            binding.textEdit.setOnClickListener {
                context.toast(R.string.msg_edit_click)
                dismiss()
            }
        }

        if (::listenerDelete.isInitialized) {
            binding.textDelete.setOnClickListener(listenerDelete)
        } else {
            binding.textDelete.setOnClickListener {
                context.toast(R.string.msg_delete_click)
                dismiss()
            }
        }
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun setOnEditClickListener(listener: View.OnClickListener) {
        listenerEdit = listener
    }

    fun setOnDeleteClickListener(listener: View.OnClickListener) {
        listenerDelete = listener
    }
}
