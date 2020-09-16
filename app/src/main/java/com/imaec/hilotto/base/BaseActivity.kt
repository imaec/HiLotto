package com.imaec.hilotto.base

import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.imaec.hilotto.R
import com.imaec.hilotto.ui.view.dialog.ProgressDialog
import java.util.*

abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes private val layoutResId: Int) : AppCompatActivity() {

    protected val TAG = this::class.java.simpleName

    protected lateinit var binding: T
    private lateinit var interstitialAd: InterstitialAd

    private val progressDialog: ProgressDialog by lazy { ProgressDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutResId)

        init()
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

    private fun init() {
        MobileAds.initialize(this) {}
    }

    private fun showAd(adId: Int, callback: () -> Unit) {
        interstitialAd = InterstitialAd(this).apply {
            adUnitId = getString(adId)
            adListener = object : AdListener() {
                override fun onAdLoaded() {
                    interstitialAd.show()
                }

                override fun onAdFailedToLoad(p0: Int) {
                    super.onAdFailedToLoad(p0)
                    Log.d(TAG, "    ## ad failed to load : $p0")
                    callback()
                }

                override fun onAdClosed() {
                    super.onAdClosed()
                    callback()
                }
            }
        }
        interstitialAd.loadAd(AdRequest.Builder().build())
    }

    fun showAd(adId: Int, isRandom: Boolean = false, callback: () -> Unit) {
        showProgress()

        if (isRandom) {
            val ran = Random().nextInt(4) + 1
            if (ran == 1) {
                showAd(adId) {
                    hideProgress()
                    callback()
                }
            } else {
                hideProgress()
                callback()
            }
        } else {
            showAd(adId) {
                hideProgress()
                callback()
            }
        }
    }
}