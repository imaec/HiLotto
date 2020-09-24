package com.imaec.hilotto.ui.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentSettingBinding
import com.imaec.hilotto.utils.Utils
import com.imaec.hilotto.viewmodel.SettingViewModel
import com.kakao.kakaolink.v2.KakaoLinkResponse
import com.kakao.kakaolink.v2.KakaoLinkService
import com.kakao.network.ErrorResult
import com.kakao.network.callback.ResponseCallback

class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private lateinit var settingViewModel: SettingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingViewModel = getViewModel(SettingViewModel::class.java)

        binding.apply {
            lifecycleOwner = this@SettingFragment
            settingViewModel = this@SettingFragment.settingViewModel
        }

        settingViewModel.setAppVersion(Utils.getVersion(context!!))
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.text_share -> {
                KakaoLinkService.getInstance()
                    .sendCustom(context, getString(R.string.template_id_app), null, object : ResponseCallback<KakaoLinkResponse>() {
                        override fun onSuccess(result: KakaoLinkResponse?) {
                            logEvent(FirebaseAnalytics.Event.SHARE, Bundle())
                        }

                        override fun onFailure(errorResult: ErrorResult?) {
                            Log.e(TAG, "    ## ${errorResult.toString()}")
                            Toast.makeText(context, R.string.msg_share_fail, Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }
    }
}