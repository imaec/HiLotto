package com.imaec.hilotto.base

import android.content.Intent
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
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.imaec.hilotto.R
import com.imaec.hilotto.ui.view.dialog.ProgressDialog
import java.util.Random

abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes private val layoutResId: Int) :
    AppCompatActivity() {

    protected val TAG = this::class.java.simpleName

    protected lateinit var binding: T
    protected lateinit var interstitialAd: InterstitialAd

    private val progressDialog: ProgressDialog by lazy { ProgressDialog(this) }
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutResId)

        init()

        FirebaseCrashlytics.getInstance().log("$TAG onCreate")
        FirebaseCrashlytics.getInstance().setCustomKey(getString(R.string.key_activity), TAG)
    }

    override fun onResume() {
        super.onResume()
        FirebaseCrashlytics.getInstance().log("$TAG onResume")
    }

    override fun onPause() {
        super.onPause()
        FirebaseCrashlytics.getInstance().log("$TAG onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        FirebaseCrashlytics.getInstance().log("$TAG onDestroy")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        FirebaseCrashlytics.getInstance().log("$TAG onActivityResult")
    }

    protected fun <V : ViewModel> getViewModel(modelClass: Class<V>, vararg repository: Any): V {
        return ViewModelProvider(this, BaseViewModelFactory(*repository)).get(modelClass)
    }

    protected fun showProgress() {
        if (!progressDialog.isShowing) progressDialog.show()
    }

    protected fun hideProgress() {
        if (progressDialog.isShowing) progressDialog.dismiss()
    }

    protected fun logEvent(key: String, bundle: Bundle) {
        firebaseAnalytics.logEvent(key, bundle)
    }

    private fun init() {
        MobileAds.initialize(this) {}
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
    }

    private fun showAd(adId: Int, onLoaded: () -> Unit, onClosed: () -> Unit) {
        interstitialAd = InterstitialAd(this).apply {
            adUnitId = getString(adId)
            adListener = object : AdListener() {
                override fun onAdLoaded() {
                    onLoaded()
                }

                override fun onAdFailedToLoad(p0: LoadAdError?) {
                    super.onAdFailedToLoad(p0)
                    Log.d(TAG, "    ## ad failed to load : $p0")
                    onClosed()
                }

                override fun onAdClosed() {
                    super.onAdClosed()
                    onClosed()
                }
            }
        }
        interstitialAd.loadAd(AdRequest.Builder().build())
    }

    fun showAd(adId: Int, isRandom: Boolean, onLoaded: () -> Unit, onClosed: () -> Unit) {
        showProgress()

        if (isRandom) {
            val ran = Random().nextInt(4) + 1
            if (ran == 1) {
                showAd(
                    adId = adId,
                    onLoaded = {
                        hideProgress()
                    },
                    onClosed = {
                        hideProgress()
                        onClosed()
                    }
                )
            } else {
                hideProgress()
                onClosed()
            }
        } else {
            showAd(
                adId = adId,
                onLoaded = {
                    hideProgress()
                    onLoaded()
                },
                onClosed = {
                    hideProgress()
                    onClosed()
                }
            )
        }
    }
}
