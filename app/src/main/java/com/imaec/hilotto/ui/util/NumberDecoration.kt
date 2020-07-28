package com.imaec.hilotto.ui.util

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.imaec.hilotto.utils.Utils

class NumberDecoration(var context: Context) : RecyclerView.ItemDecoration() {

    private val offset1 = Utils.dp(context, 2)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildLayoutPosition(view)
        val size = if (parent.adapter != null) parent.adapter!!.itemCount else 0

        outRect.left = offset1
        outRect.right = offset1
        when (position) {
            0 -> outRect.left = 0
            size-1 -> outRect.right = 0
        }
    }
}