package com.imaec.hilotto.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
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

abstract class BaseFragment<VDB : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : Fragment() {

    protected val TAG = this::class.java.simpleName

    protected lateinit var binding: VDB
    protected var interstitialAd: InterstitialAd? = null

    private val progressDialog: ProgressDialog by lazy { ProgressDialog(requireContext()) }
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<VDB>(
            inflater,
            layoutResId,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        FirebaseCrashlytics.getInstance().log("$TAG onViewCreated")
        FirebaseCrashlytics.getInstance().setCustomKey(getString(R.string.key_fragment), TAG)
        logEvent(
            getString(R.string.event_screen_fragment),
            Bundle().apply {
                putString(getString(R.string.key_screen_fragment), TAG)
            }
        )
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
        MobileAds.initialize(requireContext()) {}
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
                        onLoaded()
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
            requireContext(), getString(adId), AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    interstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    this@BaseFragment.interstitialAd = interstitialAd
                    this@BaseFragment.interstitialAd?.fullScreenContentCallback =
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
