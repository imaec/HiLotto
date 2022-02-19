package com.imaec.hilotto.ui.view.fragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.firebase.analytics.FirebaseAnalytics
import com.imaec.hilotto.R
import com.imaec.hilotto.REQUEST_CREATE_FILE
import com.imaec.hilotto.REQUEST_OPEN_DOCUMENT
import com.imaec.hilotto.REQUEST_PERMISSION_EXPORT
import com.imaec.hilotto.REQUEST_PERMISSION_IMPORT
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentSettingBinding
import com.imaec.hilotto.ui.view.dialog.CommonDialog
import com.imaec.hilotto.ui.view.dialog.InfoDialog
import com.imaec.hilotto.ui.view.dialog.InputDialog
import com.imaec.hilotto.utils.SharedPreferenceUtil
import com.imaec.hilotto.utils.Utils
import com.imaec.hilotto.viewmodel.MyViewModel
import com.imaec.hilotto.viewmodel.SettingViewModel
import com.kakao.kakaolink.v2.KakaoLinkResponse
import com.kakao.kakaolink.v2.KakaoLinkService
import com.kakao.network.ErrorResult
import com.kakao.network.callback.ResponseCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_search.*
import java.io.File

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private val viewModel by viewModels<SettingViewModel>()
    private val myViewModel by activityViewModels<MyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = this@SettingFragment
            vm = this@SettingFragment.viewModel
        }

        viewModel.apply {
            setSettingStatistics(
                "${
                SharedPreferenceUtil.getInt(
                    requireContext(),
                    SharedPreferenceUtil.KEY.PREF_SETTING_STATISTICS,
                    20
                )
                }회"
            )
            setAppVersion(Utils.getVersion(requireContext()))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_CREATE_FILE -> {
                if (resultCode == RESULT_OK) {
                    data?.data?.let {
                        val result =
                            viewModel.export(requireContext(), myViewModel.listNumber.value!!, it)
                        Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                    } ?: run {
                        Toast.makeText(context, R.string.msg_unknown_error, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
            REQUEST_OPEN_DOCUMENT -> {
                if (resultCode == RESULT_OK) {
                    data?.data?.let {
                        viewModel.import(requireContext(), it)?.let { entities ->
                            myViewModel.saveNumbers(entities.toList()) {
                                Toast.makeText(
                                    context,
                                    R.string.msg_success_import_my_number,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } ?: run {
                            Toast.makeText(
                                context,
                                R.string.msg_fail_import_my_number,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } ?: run {
                        Toast.makeText(context, R.string.msg_unknown_error, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        if (!hidden) myViewModel.getNumbers()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var grantResult = 0
        grantResults.forEach {
            if (it == 0) grantResult++
        }
        if (permissions.size == grantResult) {
            when (requestCode) {
                REQUEST_PERMISSION_EXPORT -> export()
                REQUEST_PERMISSION_IMPORT -> import()
            }
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.view_setting_statistics -> {
                InputDialog(requireContext()).apply {
                    setTitle(getString(R.string.setting_statistics))
                    setHint(getString(R.string.msg_setting_statistics_hint))
                    setSearch(getString(R.string.setting))
                    setOnSearchClickListener {
                        val result = viewModel.checkSettingRound(
                            edit_search.text.toString(),
                            SharedPreferenceUtil.getInt(
                                context,
                                SharedPreferenceUtil.KEY.PREF_CUR_DRW_NO,
                                1
                            )
                        )
                        if (result == "OK") {
                            SharedPreferenceUtil.putValue(
                                context,
                                SharedPreferenceUtil.KEY.PREF_SETTING_STATISTICS,
                                edit_search.text.toString().toInt()
                            )
                            viewModel.setSettingStatistics("${edit_search.text}회")
                            Toast.makeText(
                                context,
                                R.string.msg_success_save_setting_statistics,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                        }
                        dismiss()
                    }
                    show()
                }
            }
            R.id.image_export_info -> InfoDialog(
                requireContext(),
                getString(R.string.msg_export_description)
            ).show()
            R.id.text_export_my_number -> {
                if (!checkPermission(REQUEST_PERMISSION_EXPORT)) return

                CommonDialog(requireContext(), getString(R.string.msg_export_info)).apply {
                    setTitle(getString(R.string.export_my_number))
                    setOnOkClickListener(
                        View.OnClickListener {
                            export()
                            dismiss()
                        }
                    )
                    show()
                }
            }
            R.id.image_import_info -> InfoDialog(
                requireContext(),
                getString(R.string.msg_import_description)
            ).show()
            R.id.text_import_my_number -> {
                if (!checkPermission(REQUEST_PERMISSION_IMPORT)) return

                CommonDialog(requireContext(), getString(R.string.msg_import_info)).apply {
                    setTitle(getString(R.string.import_my_number))
                    setOnOkClickListener(
                        View.OnClickListener {
                            import()
                            dismiss()
                        }
                    )
                    show()
                }
            }
            R.id.text_share -> {
                KakaoLinkService.getInstance()
                    .sendCustom(
                        context, getString(R.string.template_id_app), null,
                        object : ResponseCallback<KakaoLinkResponse>() {
                            override fun onSuccess(result: KakaoLinkResponse?) {
                                logEvent(FirebaseAnalytics.Event.SHARE, Bundle())
                            }

                            override fun onFailure(errorResult: ErrorResult?) {
                                Toast.makeText(context, R.string.msg_share_fail, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    )
            }
        }
    }

    private fun checkPermission(requestCode: Int): Boolean {
        return if (
            requireActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED
        ) {
            true
        } else {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode)
            false
        }
    }

    private fun export() {
        myViewModel.listNumber.value?.let {
            if (Build.VERSION.SDK_INT < 29) {
                val result = viewModel.export(
                    it,
                    File(
                        "${Environment.getExternalStorageDirectory().absolutePath}/" +
                            "${Environment.DIRECTORY_DOWNLOADS}"
                    )
                )
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
            } else {
                startActivityForResult(
                    Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                        type = "application/json"
                        putExtra(Intent.EXTRA_TITLE, "myNumber.json")
                    },
                    REQUEST_CREATE_FILE
                )
            }
        } ?: run {
            Toast.makeText(context, R.string.msg_unknown_error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun import() {
        if (Build.VERSION.SDK_INT < 29) {
            viewModel.import(
                requireContext(),
                Uri.parse(
                    "file://${File(
                        "${Environment.getExternalStorageDirectory().absolutePath}/" +
                            "${Environment.DIRECTORY_DOWNLOADS}"
                    ).path}/myNumber.json"
                )
            )?.let { entities ->
                myViewModel.saveNumbers(entities.toList()) {
                    Toast.makeText(
                        context,
                        R.string.msg_success_import_my_number,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } ?: run {
                Toast.makeText(context, R.string.msg_fail_import_my_number, Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            startActivityForResult(
                Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    type = "application/json"
                },
                REQUEST_OPEN_DOCUMENT
            )
        }
    }
}
