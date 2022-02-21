package com.imaec.hilotto.ui.setting

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.core.content.ContextCompat.checkSelfPermission
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
import com.imaec.hilotto.ui.main.MainViewModel
import com.imaec.hilotto.ui.view.dialog.CommonDialog
import com.imaec.hilotto.ui.view.dialog.InputDialog
import com.imaec.hilotto.utils.SharedPreferenceUtil
import com.imaec.hilotto.utils.toast
import com.imaec.hilotto.ui.my.MyViewModel
import com.imaec.hilotto.utils.getVersion
import com.imaec.hilotto.utils.showInfoDialog
import com.kakao.kakaolink.v2.KakaoLinkResponse
import com.kakao.kakaolink.v2.KakaoLinkService
import com.kakao.network.ErrorResult
import com.kakao.network.callback.ResponseCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_search.*
import timber.log.Timber
import java.io.File

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private val viewModel by viewModels<SettingViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val myViewModel by activityViewModels<MyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupData()
        setupObserver()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_CREATE_FILE -> {
                if (resultCode == RESULT_OK) {
                    data?.data?.let {
                        val result =
                            viewModel.export(requireContext(), myViewModel.myNumberList.value!!, it)
                        toast(result)
                    } ?: run {
                        toast(R.string.msg_unknown_error)
                    }
                }
            }
            REQUEST_OPEN_DOCUMENT -> {
                if (resultCode == RESULT_OK) {
                    data?.data?.let {
                        viewModel.import(requireContext(), it)?.let { entities ->
                            myViewModel.saveNumbers(entities.toList()) {
                                toast(R.string.msg_success_import_my_number)
                            }
                        } ?: run {
                            toast(R.string.msg_fail_import_my_number)
                        }
                    } ?: run {
                        toast(R.string.msg_unknown_error)
                    }
                }
            }
        }
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

    private fun setupBinding() {
        with(binding) {
            vm = viewModel
        }
    }

    private fun setupData() {
        with(viewModel) {
            setSettingStatistics(
                SharedPreferenceUtil.getInt(
                    context = requireContext(),
                    key = SharedPreferenceUtil.KEY.PREF_SETTING_STATISTICS,
                    def = 20
                )
            )
            setAppVersion(getVersion())
        }
    }

    private fun setupObserver() {
        viewModel.state.observe(this) {
            when (it) {
                SettingState.OnClickSettingStatistics -> {
                    showInputDialog()
                }
                SettingState.OnClickExport -> {
                    if (!checkPermission(REQUEST_PERMISSION_EXPORT)) return@observe

                    CommonDialog(requireContext(), getString(R.string.msg_export_info)).apply {
                        setTitle(getString(R.string.export_my_number))
                        setOnOkClickListener {
                            export()
                            dismiss()
                        }
                        show()
                    }
                }
                SettingState.OnClickExportInfo -> {
                    showInfoDialog(getString(R.string.msg_export_description))
                }
                SettingState.OnClickImport -> {
                    if (!checkPermission(REQUEST_PERMISSION_IMPORT)) return@observe

                    CommonDialog(requireContext(), getString(R.string.msg_import_info)).apply {
                        setTitle(getString(R.string.import_my_number))
                        setOnOkClickListener {
                            import()
                            dismiss()
                        }
                        show()
                    }
                }
                SettingState.OnClickImportInfo -> {
                    showInfoDialog(getString(R.string.msg_import_description))
                }
                SettingState.OnClickShare -> {
                    share()
                }
            }
        }
    }

    private fun showInputDialog() {
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
                    viewModel.setSettingStatistics(edit_search.text.toString().toInt())
                    mainViewModel.changeSetting(true)
                    toast(R.string.msg_success_save_setting_statistics)
                } else {
                    toast(result)
                }
                dismiss()
            }
            show()
        }
    }

    private fun checkPermission(requestCode: Int): Boolean {
        return if (
            checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED
        ) {
            true
        } else {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode)
            false
        }
    }

    private fun export() {
        myViewModel.myNumberList.value?.let {
            if (Build.VERSION.SDK_INT < 29) {
                val result = viewModel.export(
                    it,
                    File(
                        "${Environment.getExternalStorageDirectory().absolutePath}/" +
                            "${Environment.DIRECTORY_DOWNLOADS}"
                    )
                )
                toast(result)
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
            toast(R.string.msg_unknown_error)
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
                    toast(R.string.msg_success_import_my_number)
                }
            } ?: run {
                toast(R.string.msg_fail_import_my_number)
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

    private fun share() {
        KakaoLinkService.getInstance()
            .sendCustom(
                context, getString(R.string.template_id_app), null,
                object : ResponseCallback<KakaoLinkResponse>() {
                    override fun onSuccess(result: KakaoLinkResponse?) {
                        logEvent(FirebaseAnalytics.Event.SHARE, Bundle())
                    }

                    override fun onFailure(errorResult: ErrorResult?) {
                        Timber.e("  ## share error : $errorResult")
                        toast(R.string.msg_share_fail)
                    }
                }
            )
    }
}
