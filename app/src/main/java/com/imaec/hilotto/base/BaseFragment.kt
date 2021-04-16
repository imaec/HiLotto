package com.imaec.hilotto.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.google.android.gms.ads.*
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.imaec.hilotto.R
import com.imaec.hilotto.ui.view.dialog.ProgressDialog
import java.util.*


abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes private val layoutResId: Int) : Fragment() {

    protected val TAG = this::class.java.simpleName

    protected lateinit var binding: T
    private lateinit var interstitialAd: InterstitialAd

    private val progressDialog: ProgressDialog by lazy { ProgressDialog(context!!) }
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        FirebaseCrashlytics.getInstance().log("$TAG onViewCreated")
        FirebaseCrashlytics.getInstance().setCustomKey(getString(R.string.key_fragment), TAG)
        logEvent(getString(R.string.event_screen_fragment), Bundle().apply {
            putString(getString(R.string.key_screen_fragment), TAG)
        })
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

    protected fun <T : ViewModel> getViewModel(modelClass: Class<T>, vararg repository: Any) : T {
        return ViewModelProvider(this, BaseViewModelFactory(*repository)).get(modelClass)
    }

    protected fun <T : ViewModel> getViewModel(modelClass: Class<T>, owner: ViewModelStoreOwner = this, vararg repository: Any) : T {
        return ViewModelProvider(owner, BaseViewModelFactory(*repository)).get(modelClass)
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
        MobileAds.initialize(context) {}
        firebaseAnalytics = FirebaseAnalytics.getInstance(context!!)
    }

    private fun showAd(adId: Int, callback: () -> Unit) {
        interstitialAd = InterstitialAd(context).apply {
            adUnitId = getString(adId)
            adListener = object : AdListener() {
                override fun onAdLoaded() {
                    interstitialAd.show()
                }

                override fun onAdFailedToLoad(p0: LoadAdError?) {
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