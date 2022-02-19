package com.imaec.hilotto.ui.util

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.imaec.hilotto.utils.Utils

class NumbersDecoration(
    var context: Context,
    offset1: Int = 8,
    offset2: Int = 16
) : RecyclerView.ItemDecoration() {

    private val offset1 = Utils.dp(context, offset1)
    private val offset2 = Utils.dp(context, offset2)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildLayoutPosition(view)
        val size = if (parent.adapter != null) parent.adapter!!.itemCount else 0

        outRect.top = offset1
        outRect.bottom = offset1
        when (position) {
            0 -> outRect.top = offset2
            size - 1 -> outRect.bottom = offset2
        }
    }
}
