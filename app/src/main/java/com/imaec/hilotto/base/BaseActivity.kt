package com.imaec.hilotto.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.imaec.hilotto.R
import com.imaec.hilotto.ui.view.dialog.ProgressDialog
import java.util.Random
import java.util.Stack

abstract class BaseActivity<VDB : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : AppCompatActivity(), BaseInterface {

    private val TAG = this::class.java.simpleName

    protected lateinit var binding: VDB
    protected var interstitialAd: InterstitialAd? = null

    private val loadingDialog: ProgressDialog by lazy { ProgressDialog(this) }
    private var loadingStack = Stack<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<VDB>(this, layoutResId).apply {
            lifecycleOwner = this@BaseActivity
        }

        MobileAds.initialize(this)

        Firebase.crashlytics.log("$TAG onCreate")
        Firebase.crashlytics.setCustomKey(getString(R.string.key_activity), TAG)
    }

    override fun onResume() {
        super.onResume()
        Firebase.crashlytics.log("$TAG onResume")
    }

    override fun onPause() {
        super.onPause()
        Firebase.crashlytics.log("$TAG onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Firebase.crashlytics.log("$TAG onDestroy")
    }

    override fun loadingState(isShow: Boolean) {
        if (isShow) showLoading() else hideLoading()
    }

    override fun closeAllLoading() {
        while (loadingStack.isNotEmpty()) {
            hideLoading()
        }
    }

    protected open fun showLoading() {
        if (!loadingDialog.isShowing && !isFinishing) {
            loadingDialog.show()
        }

        loadingStack.push(0)
    }

    protected open fun hideLoading() {
        if (!loadingStack.isEmpty()) loadingStack.pop()
        if (loadingStack.isEmpty()) loadingDialog.dismiss()
    }

    fun setupLoadingObserver(vararg viewModels: BaseViewModel) {
        lifecycleScope.launchWhenStarted {
            viewModels.forEach { viewModel ->
                viewModel.loadingState.loading.observe(this@BaseActivity) {
                    if (it) showLoading() else hideLoading()
                }
            }
        }
    }

    protected fun logEvent(key: String, bundle: Bundle) {
        Firebase.analytics.logEvent(key, bundle)
    }

    fun showAd(
        adId: Int,
        isRandom: Boolean,
        showLoading: Boolean = true,
        onLoaded: () -> Unit,
        onClosed: () -> Unit
    ) {
        if (showLoading) showLoading()

        val ran = Random().nextInt(4) + 1
        if (!isRandom || (isRandom && ran == 1)) {
            showAd(
                adId = adId,
                onLoaded = {
                    hideLoading()
                    onLoaded()
                },
                onClosed = {
                    hideLoading()
                    onClosed()
                }
            )
        } else {
            hideLoading()
            onClosed()
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
