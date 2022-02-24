package com.imaec.data.prefs

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.imaec.domain.prefs.DataStoreStorage
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreStorageImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreStorage {

    companion object {
        const val PREFS_NAME = "HiLotto"
    }

    object PrefsKeys {
        val PREF_CUR_DRW_NO = intPreferencesKey("PREF_CUR_DRW_NO")
        val PREF_RECOMMEND_CONDITION_SUM = booleanPreferencesKey("PREF_RECOMMEND_CONDITION_SUM")
        val PREF_RECOMMEND_CONDITION_PICK = booleanPreferencesKey("PREF_RECOMMEND_CONDITION_PICK")
        val PREF_RECOMMEND_CONDITION_ODD_EVEN = booleanPreferencesKey(
            "PREF_RECOMMEND_CONDITION_ODD_EVEN"
        )
        val PREF_RECOMMEND_CONDITION_ALL = booleanPreferencesKey("PREF_RECOMMEND_CONDITION_ALL")
        val PREF_SETTING_STATISTICS = intPreferencesKey("PREF_SETTING_STATISTICS")
    }

    override suspend fun curDrwNo(): Int = dataStore.data.map {
        it[PrefsKeys.PREF_CUR_DRW_NO] ?: 1
    }.firstOrNull() ?: 1

    override suspend fun setCurDrwNo(curDrwNo: Int) {
        dataStore.edit {
            it[PrefsKeys.PREF_CUR_DRW_NO] = curDrwNo
        }
    }

    override suspend fun isRecommendSumCheck(): Boolean = dataStore.data.map {
        it[PrefsKeys.PREF_RECOMMEND_CONDITION_SUM] ?: true
    }.firstOrNull() ?: true

    override suspend fun setRecommendSumCheck(isCheck: Boolean) {
        dataStore.edit {
            it[PrefsKeys.PREF_RECOMMEND_CONDITION_SUM] = isCheck
        }
    }

    override suspend fun isRecommendPickCheck(): Boolean = dataStore.data.map {
        it[PrefsKeys.PREF_RECOMMEND_CONDITION_PICK] ?: true
    }.firstOrNull() ?: true

    override suspend fun setRecommendPickCheck(isCheck: Boolean) {
        dataStore.edit {
            it[PrefsKeys.PREF_RECOMMEND_CONDITION_PICK] = isCheck
        }
    }

    override suspend fun isRecommendOddEvenCheck(): Boolean = dataStore.data.map {
        it[PrefsKeys.PREF_RECOMMEND_CONDITION_ODD_EVEN] ?: true
    }.firstOrNull() ?: true

    override suspend fun setRecommendOddEvenCheck(isCheck: Boolean) {
        dataStore.edit {
            it[PrefsKeys.PREF_RECOMMEND_CONDITION_ODD_EVEN] = isCheck
        }
    }

    override suspend fun isRecommendAllCheck(): Boolean = dataStore.data.map {
        it[PrefsKeys.PREF_RECOMMEND_CONDITION_ALL] ?: true
    }.firstOrNull() ?: true

    override suspend fun setRecommendAllCheck(isCheck: Boolean) {
        dataStore.edit {
            it[PrefsKeys.PREF_RECOMMEND_CONDITION_ALL] = isCheck
        }
    }

    override suspend fun statisticsRound(): Int = dataStore.data.map {
        it[PrefsKeys.PREF_SETTING_STATISTICS] ?: 20
    }.firstOrNull() ?: 20

    override suspend fun setStatisticsRound(round: Int) {
        dataStore.edit {
            it[PrefsKeys.PREF_SETTING_STATISTICS] = round
        }
    }
}
