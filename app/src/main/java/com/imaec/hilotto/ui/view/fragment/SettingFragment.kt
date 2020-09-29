package com.imaec.hilotto.ui.view.fragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.imaec.hilotto.R
import com.imaec.hilotto.REQUEST_CREATE_FILE
import com.imaec.hilotto.REQUEST_PERMISSION_EXPORT
import com.imaec.hilotto.REQUEST_PERMISSION_IMPORT
import com.imaec.hilotto.base.BaseFragment
import com.imaec.hilotto.databinding.FragmentSettingBinding
import com.imaec.hilotto.repository.NumberRepository
import com.imaec.hilotto.room.AppDatabase
import com.imaec.hilotto.room.dao.NumberDao
import com.imaec.hilotto.ui.view.dialog.InputDialog
import com.imaec.hilotto.utils.SharedPreferenceUtil
import com.imaec.hilotto.utils.Utils
import com.imaec.hilotto.viewmodel.MyViewModel
import com.imaec.hilotto.viewmodel.SettingViewModel
import com.kakao.kakaolink.v2.KakaoLinkResponse
import com.kakao.kakaolink.v2.KakaoLinkService
import com.kakao.network.ErrorResult
import com.kakao.network.callback.ResponseCallback
import kotlinx.android.synthetic.main.dialog_search.*
import java.io.File


class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private lateinit var settingViewModel: SettingViewModel
    private lateinit var myViewModel: MyViewModel
    private lateinit var numberDao: NumberDao
    private lateinit var numberRepository: NumberRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        settingViewModel = getViewModel(SettingViewModel::class.java)
        myViewModel = getViewModel(MyViewModel::class.java, numberRepository)

        binding.apply {
            lifecycleOwner = this@SettingFragment
            settingViewModel = this@SettingFragment.settingViewModel
        }

        settingViewModel.apply {
            setSettingStatistics("${SharedPreferenceUtil.getInt(context!!, SharedPreferenceUtil.KEY.PREF_SETTING_STATISTICS, 20)}회")
            setAppVersion(Utils.getVersion(context!!))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CREATE_FILE) {
            when (resultCode) {
                RESULT_OK -> {
                    data?.data?.let {
                        Log.d(TAG, "    ## uri : $it")
                        // writeInFile(data.data, "bison is bision")
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
                InputDialog(context!!).apply {
                    setTitle(getString(R.string.setting_statistics))
                    setHint(getString(R.string.msg_setting_statistics_hint))
                    setSearch(getString(R.string.setting))
                    setOnSearchClickListener(View.OnClickListener {
                        val result = settingViewModel.checkSettingRound(edit_search.text.toString(), SharedPreferenceUtil.getInt(context, SharedPreferenceUtil.KEY.PREF_CUR_DRW_NO, 1))
                        if (result == "OK") {
                            SharedPreferenceUtil.putValue(context, SharedPreferenceUtil.KEY.PREF_SETTING_STATISTICS, edit_search.text.toString().toInt())
                            settingViewModel.setSettingStatistics("${edit_search.text}회")
                            Toast.makeText(context, R.string.msg_success_save_setting_statistics, Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                        }
                        dismiss()
                    })
                    show()
                }
            }
            R.id.text_export_my_number -> {
                if (!checkPermission(REQUEST_PERMISSION_EXPORT)) return
                export()
            }
            R.id.image_export_info -> {
            }
            R.id.text_import_my_number -> {
                if (!checkPermission(REQUEST_PERMISSION_IMPORT)) return
                import()
            }
            R.id.image_import_info -> {
            }
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

    private fun init() {
        numberDao = AppDatabase.getInstance(context!!).numberDao()
        numberRepository = NumberRepository(numberDao)
    }

    private fun checkPermission(requestCode: Int): Boolean {
        return if (activity!!.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            true
        } else {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode)
            false
        }
    }

    private fun export() {
        myViewModel.listNumber.value?.let {
            val result = settingViewModel.export(
                it,
                if (Build.VERSION.SDK_INT < 29) {
                    File("${Environment.getExternalStorageDirectory().absolutePath}/${getString(R.string.app_name)}")
                } else {
                    val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                        type = "application/json"
                        putExtra(Intent.EXTRA_TITLE, "test.json")
                    }
                    startActivityForResult(intent, REQUEST_CREATE_FILE)

                    context!!.getExternalFilesDir("/${getString(R.string.app_name)}")!!
                }
            )
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
        } ?: run {
            Toast.makeText(context, R.string.msg_unknown_error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun import() {

    }
}