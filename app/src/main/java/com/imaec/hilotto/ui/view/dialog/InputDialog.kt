package com.imaec.hilotto.ui.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import com.imaec.hilotto.R
import kotlinx.android.synthetic.main.dialog_common.*
import kotlinx.android.synthetic.main.dialog_common.text_cancel
import kotlinx.android.synthetic.main.dialog_edit.text_title
import kotlinx.android.synthetic.main.dialog_search.*
import kotlinx.android.synthetic.main.dialog_search.view.*

class InputDialog(context: Context) : Dialog(context) {

    private var title = ""
    private var hint = ""
    private var search = ""
    private var cancel = ""
    private lateinit var listenerSearch: (String) -> Unit
    private lateinit var listenerCancel: View.OnClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_search)

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

        if (title.isEmpty()) title = context.getString(R.string.search)
        if (hint.isEmpty()) hint = context.getString(R.string.msg_search_hint)
        if (search.isEmpty()) search = context.getString(R.string.search)
        if (cancel.isEmpty()) cancel = context.getString(R.string.cancel)

        initLayout()
    }

    private fun initLayout() {
        text_title.text = title
        edit_search.hint = hint
        text_search.text = search
        text_cancel.text = cancel

        if (::listenerSearch.isInitialized) text_search.setOnClickListener {
            listenerSearch(edit_search.text.toString())
        }
        else text_search.setOnClickListener { dismiss() }

        if (::listenerCancel.isInitialized) text_cancel.setOnClickListener(listenerCancel)
        else text_cancel.setOnClickListener { dismiss() }

        edit_search.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                text_search.performClick()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun setHint(hint: String) {
        this.hint = hint
    }

    fun setSearch(search: String) {
        this.search = search
    }

    fun setCancel(cancel: String) {
        this.cancel = cancel
    }

    fun setOnSearchClickListener(listener: (String) -> Unit) {
        listenerSearch = listener
    }

    fun setOnCancelClickListener(listener: View.OnClickListener) {
        listenerCancel = listener
    }

    interface OnClickSearchListener {
        fun onClick(keyword: String)
    }
}
