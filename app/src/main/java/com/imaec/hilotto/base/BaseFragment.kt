package com.imaec.hilotto.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
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
import java.util.Random

abstract class BaseFragment<VDB : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : Fragment() {

    private val TAG = this::class.java.simpleName

    protected lateinit var binding: VDB
    protected var interstitialAd: InterstitialAd? = null

    private lateinit var baseInterface: BaseInterface

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

        MobileAds.initialize(requireContext())

        Firebase.crashlytics.log("$TAG onViewCreated")
        Firebase.crashlytics.setCustomKey(getString(R.string.key_fragment), TAG)
        logEvent(
            getString(R.string.event_screen_fragment),
            Bundle().apply {
                putString(getString(R.string.key_screen_fragment), TAG)
            }
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is BaseInterface) {
            baseInterface = context
        }
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

    protected open fun showLoading() {
        baseInterface.loadingState(true)
    }

    protected open fun hideLoading() {
        baseInterface.loadingState(false)
    }

    fun setupLoadingObserver(vararg viewModels: BaseViewModel) {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModels.forEach { viewModel ->
                viewModel.loadingState.loading.observe(viewLifecycleOwner) {
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
