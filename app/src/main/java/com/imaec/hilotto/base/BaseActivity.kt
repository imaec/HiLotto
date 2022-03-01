package com.imaec.hilotto.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import com.imaec.hilotto.R
import com.imaec.hilotto.ui.view.dialog.ProgressDialog
import java.util.Random

abstract class BaseActivity<VDB : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : AppCompatActivity() {

    protected val TAG = this::class.java.simpleName

    protected lateinit var binding: VDB
    protected var interstitialAd: InterstitialAd? = null

    private val progressDialog: ProgressDialog by lazy { ProgressDialog(this) }
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<VDB>(this, layoutResId).apply {
            lifecycleOwner = this@BaseActivity
        }

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
        firebaseAnalytics = Firebase.analytics
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

    private fun showAd(adId: Int, onLoaded: () -> Unit, onClosed: () -> Unit) {
        InterstitialAd.load(
            this, getString(adId), AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    interstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    this@BaseActivity.interstitialAd = interstitialAd
                    this@BaseActivity.interstitialAd?.fullScreenContentCallback =
                        object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent()
                                onClosed()
                            }

                            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                                super.onAdFailedToShowFullScreenContent(p0)
                                onClosed()
                            }
                        }
                    onLoaded()
                }
            }
        )
    }
}
