package com.imaec.hilotto.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imaec.hilotto.ui.view.dialog.ProgressDialog

abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes private val layoutResId: Int) : AppCompatActivity() {

    protected val TAG = this::class.java.simpleName

    protected lateinit var binding: T

    private val progressDialog: ProgressDialog by lazy { ProgressDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutResId)
    }

    protected fun <V : ViewModel> getViewModel(modelClass: Class<V>, vararg repository: Any) : V {
        return ViewModelProvider(this, BaseViewModelFactory(*repository)).get(modelClass)
    }

    protected fun showProgress() {
        if (!progressDialog.isShowing) progressDialog.show()
    }

    protected fun hideProgress() {
        if (progressDialog.isShowing) progressDialog.dismiss()
    }
}