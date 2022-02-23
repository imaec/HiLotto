package com.imaec.hilotto.ui.setting

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.gson.Gson
import com.imaec.hilotto.R
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentSettingBinding
import com.imaec.hilotto.room.entity.NumberEntity
import com.imaec.hilotto.ui.main.MainViewModel
import com.imaec.hilotto.ui.view.dialog.CommonDialog
import com.imaec.hilotto.ui.view.dialog.InputDialog
import com.imaec.hilotto.utils.SharedPreferenceUtil
import com.imaec.hilotto.utils.getVersion
import com.imaec.hilotto.utils.showInfoDialog
import com.imaec.hilotto.utils.toast
import com.kakao.sdk.common.util.KakaoCustomTabsClient
import com.kakao.sdk.link.LinkClient
import com.kakao.sdk.link.WebSharerClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_search.*
import java.io.BufferedWriter
import java.io.File
import java.io.IOException
import java.io.OutputStreamWriter

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private val viewModel by viewModels<SettingViewModel>()
    private val mainViewModel by activityViewModels<MainViewModel>()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupData()
        setupObserver()
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
                    if (checkPermission()) {
                        CommonDialog(requireContext(), getString(R.string.msg_export_info)).apply {
                            setTitle(getString(R.string.export_my_number))
                            setOnOkClickListener {
                                viewModel.export(
                                    "${Environment.getExternalStorageDirectory().absolutePath}/" +
                                        Environment.DIRECTORY_DOWNLOADS
                                )
                                dismiss()
                            }
                            show()
                        }
                    } else {
                        requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    }
                }
                SettingState.ExportStep2 -> {
                    moveDocument(DocumentAction.CREATE)
                }
                SettingState.OnClickExportInfo -> {
                    showInfoDialog(getString(R.string.msg_export_description))
                }
                SettingState.OnClickImport -> {
                    if (checkPermission()) {
                        CommonDialog(requireContext(), getString(R.string.msg_import_info)).apply {
                            setTitle(getString(R.string.import_my_number))
                            setOnOkClickListener {
                                import()
                                dismiss()
                            }
                            show()
                        }
                    } else {
                        requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    }
                }
                SettingState.ImportStep2 -> {
                    moveDocument(DocumentAction.OPEN)
                }
                SettingState.OnClickImportInfo -> {
                    showInfoDialog(getString(R.string.msg_import_description))
                }
                SettingState.OnClickShare -> {
                    share()
                }
                is SettingState.ShowToast -> {
                    toast(it.message)
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
                        context = context,
                        key = SharedPreferenceUtil.KEY.PREF_CUR_DRW_NO,
                        def = 1003
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

    private fun checkPermission(): Boolean {
        return checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun export(numberList: List<NumberEntity>, uri: Uri): Int {
        return try {
            val jsonString = Gson().toJson(numberList)
            val outputStream = requireContext().contentResolver.openOutputStream(uri)
            BufferedWriter(OutputStreamWriter(outputStream!!)).apply {
                write(jsonString)
                flush()
                close()
            }
            R.string.msg_success_export_my_number
        } catch (e: IOException) {
            R.string.msg_fail_export_my_number
        }
    }

    private fun import() {
        if (Build.VERSION.SDK_INT >= 30) {
            viewModel.import()
        } else {
            val uri = Uri.parse(
                "file://${File(
                    "${Environment.getExternalStorageDirectory().absolutePath}/" +
                        Environment.DIRECTORY_DOWNLOADS
                ).path}/myNumber.json"
            )
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            viewModel.import(inputStream)
        }
    }

    private fun moveDocument(documentAction: DocumentAction) {
        val action = when (documentAction) {
            DocumentAction.OPEN -> Intent.ACTION_OPEN_DOCUMENT
            DocumentAction.CREATE -> Intent.ACTION_CREATE_DOCUMENT
        }
        requireActivity().activityResultRegistry.register(
            "",
            ActivityResultContracts.StartActivityForResult()
        ) {
            when (documentAction) {
                DocumentAction.OPEN -> {
                    if (it.resultCode == RESULT_OK) {
                        it.data?.data?.let { uri ->
                            val inputStream = requireContext().contentResolver.openInputStream(uri)
                            viewModel.import(inputStream)
                        } ?: run {
                            toast(R.string.msg_unknown_error)
                        }
                    }
                }
                DocumentAction.CREATE -> {
                    if (it.resultCode == RESULT_OK) {
                        it.data?.data?.let { uri ->
                            val result = export(viewModel.numberList, uri)
                            toast(result)
                        } ?: run {
                            toast(R.string.msg_unknown_error)
                        }
                    }
                }
            }
        }.launch(
            Intent(action).apply {
                type = "application/json"
                putExtra(Intent.EXTRA_TITLE, "myNumber.json")
            }
        )
    }

    private fun share() {
        val templateId = getString(R.string.template_id_app).toLong()
        if (LinkClient.instance.isKakaoLinkAvailable(requireContext())) {
            LinkClient.instance.customTemplate(
                context = requireContext(),
                templateId = templateId
            ) { linkResult, error ->
                if (error != null) {
                    toast(R.string.msg_share_fail)
                } else if (linkResult != null) {
                    startActivity(linkResult.intent)
                    logEvent(FirebaseAnalytics.Event.SHARE, Bundle())
                }
            }
        } else {
            val sharerUrl = WebSharerClient.instance.customTemplateUri(templateId)
            try {
                KakaoCustomTabsClient.openWithDefault(requireContext(), sharerUrl)
            } catch (e: UnsupportedOperationException) {
                try {
                    KakaoCustomTabsClient.open(requireContext(), sharerUrl)
                } catch (e: ActivityNotFoundException) {
                    toast(R.string.msg_share_fail)
                }
            }
        }
    }

    private enum class DocumentAction {
        OPEN, CREATE
    }
}
