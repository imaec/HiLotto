package com.imaec.hilotto.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes private val layoutResId: Int) : AppCompatActivity() {

    protected val TAG = this::class.java.simpleName

    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutResId)
    }

    protected fun <V : ViewModel> getViewModel(modelClass: Class<V>, vararg repository: Any) : V {
        return ViewModelProvider(this, BaseViewModelFactory(*repository)).get(modelClass)
    }
}