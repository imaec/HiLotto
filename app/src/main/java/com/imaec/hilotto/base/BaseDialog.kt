package com.imaec.hilotto.base

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.util.DisplayMetrics

abstract class BaseDialog<VDB : ViewDataBinding>(
    context: Context,
    @LayoutRes private val layoutResId: Int
) : AppCompatDialog(context) {

    protected lateinit var binding: VDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, layoutResId, null, false)
        setContentView(binding.root)

        val windowWidth = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window?.windowManager?.currentWindowMetrics?.bounds?.width() ?: -1
        } else {
            val displayMetrics = DisplayMetrics()
            window?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
        window?.apply {
            attributes = WindowManager.LayoutParams().apply {
                copyFrom(window?.attributes)
                width = if (windowWidth == -1) windowWidth else (windowWidth * 0.8).toInt()
                height = WindowManager.LayoutParams.WRAP_CONTENT
            }
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}
