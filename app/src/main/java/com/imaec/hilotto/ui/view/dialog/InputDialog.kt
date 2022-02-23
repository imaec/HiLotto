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
import androidx.databinding.DataBindingUtil
import com.imaec.hilotto.R
import com.imaec.hilotto.databinding.DialogSearchBinding

class InputDialog(context: Context) : Dialog(context) {

    private lateinit var binding: DialogSearchBinding

    private var title = ""
    private var hint = ""
    private var search = ""
    private var cancel = ""
    private lateinit var listenerSearch: (String) -> Unit
    private lateinit var listenerCancel: View.OnClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_search, null, false)
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

        if (title.isEmpty()) title = context.getString(R.string.search)
        if (hint.isEmpty()) hint = context.getString(R.string.msg_search_hint)
        if (search.isEmpty()) search = context.getString(R.string.search)
        if (cancel.isEmpty()) cancel = context.getString(R.string.cancel)

        initLayout()
    }

    private fun initLayout() {
        binding.textTitle.text = title
        binding.editSearch.hint = hint
        binding.textSearch.text = search
        binding.textCancel.text = cancel

        if (::listenerSearch.isInitialized) binding.textSearch.setOnClickListener {
            listenerSearch(binding.editSearch.text.toString())
        }
        else binding.textSearch.setOnClickListener { dismiss() }

        if (::listenerCancel.isInitialized) binding.textCancel.setOnClickListener(listenerCancel)
        else binding.textCancel.setOnClickListener { dismiss() }

        binding.editSearch.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                binding.textSearch.performClick()
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
