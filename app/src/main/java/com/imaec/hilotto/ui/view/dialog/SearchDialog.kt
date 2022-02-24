package com.imaec.hilotto.ui.view.dialog

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseDialog
import com.imaec.hilotto.databinding.DialogSearchBinding

class SearchDialog(
    context: Context,
    private val title: String,
    private val hint: String,
    private val search: String = "검색",
    private val cancel: String = "취소",
    private val searchCallback: (String) -> Unit,
    private val cancelCallback: () -> Unit = {}
) : BaseDialog<DialogSearchBinding>(context, R.layout.dialog_search) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupLayout()
    }

    private fun setupLayout() {
        with(binding) {
            tvTitle.text = title
            etSearch.hint = hint
            tvSearch.text = search
            tvCancel.text = cancel

            tvSearch.setOnClickListener {
                dismiss()
                searchCallback(etSearch.text.toString())
            }
            tvCancel.setOnClickListener {
                dismiss()
                cancelCallback()
            }

            etSearch.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    tvSearch.performClick()
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
        }
    }
}
