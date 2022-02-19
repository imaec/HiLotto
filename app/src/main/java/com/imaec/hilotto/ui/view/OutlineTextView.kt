package com.imaec.hilotto.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.imaec.hilotto.R

class OutlineTextView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {

    private var hasStroke = false
    private var mStrokeWidth = 0.0f
    private var mStrokeColor = 0

    init {
        initView(context, attrs)
    }

    override fun onDraw(canvas: Canvas?) {
        if (hasStroke) {
            val states = textColors
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = mStrokeWidth
            setTextColor(mStrokeColor)
            super.onDraw(canvas)
            paint.style = Paint.Style.FILL
            setTextColor(states)
        }
        super.onDraw(canvas)
    }

    private fun initView(context: Context, attrs: AttributeSet) {
        @SuppressLint("Recycle") val typeArray =
            context.obtainStyledAttributes(attrs, R.styleable.OutlineTextView)
        hasStroke = typeArray.getBoolean(R.styleable.OutlineTextView_textStroke, false)
        mStrokeWidth = typeArray.getDimension(R.styleable.OutlineTextView_textStrokeWidth, 0.0f)
        mStrokeColor = typeArray.getColor(R.styleable.OutlineTextView_textStrokeColor, -0x1)
    }
}
