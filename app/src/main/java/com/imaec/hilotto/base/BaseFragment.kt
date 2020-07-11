package com.imaec.hilotto.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.imaec.hilotto.ui.view.dialog.ProgressDialog


abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes private val layoutResId: Int) : Fragment() {

    protected val TAG = this::class.java.simpleName

    protected lateinit var binding: T

    private val progressDialog: ProgressDialog by lazy { ProgressDialog(context!!) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    protected fun <T : ViewModel> getViewModel(modelClass: Class<T>, vararg repository: Any) : T {
        return ViewModelProvider(this, BaseViewModelFactory(*repository)).get(modelClass)
    }

    protected fun <T : ViewModel> getSharedViewModel(modelClass: Class<T>, vararg repository: Any) : T {
        return ViewModelProvider(activity!!, BaseViewModelFactory(*repository)).get(modelClass)
    }

    protected fun showProgress() {
        if (!progressDialog.isShowing) progressDialog.show()
    }

    protected fun hideProgress() {
        if (progressDialog.isShowing) progressDialog.dismiss()
    }
}