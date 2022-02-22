package com.imaec.hilotto.ui.setting

sealed class SettingState {

    object OnClickSettingStatistics : SettingState()

    object OnClickExport : SettingState()

    object OnClickImport : SettingState()

    object OnClickImportInfo : SettingState()

    object OnClickShare : SettingState()

    object OnClickExportInfo : SettingState()

    object ExportStep2 : SettingState()

    object ImportStep2 : SettingState()

    data class ShowToast(val message: String) : SettingState()
}
