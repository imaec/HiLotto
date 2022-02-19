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
import kotlinx.android.synthetic.main.dialog_edit.*

class EditDialog(context: Context) : Dialog(context) {

    private var title = "안내"
    private lateinit var listenerEdit: View.OnClickListener
    private lateinit var listenerDelete: View.OnClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_edit)

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

        if (::listenerEdit.isInitialized) {
            text_edit.setOnClickListener(listenerEdit)
        } else {
            text_edit.setOnClickListener {
                Toast.makeText(context, R.string.msg_edit_click, Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }

        if (::listenerDelete.isInitialized) {
            text_delete.setOnClickListener(listenerDelete)
        } else {
            text_delete.setOnClickListener {
                Toast.makeText(context, R.string.msg_delete_click, Toast.LENGTH_SHORT).show()
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
