package com.imaec.domain.usecase.preferences

import com.imaec.domain.prefs.DataStoreStorage
import javax.inject.Inject

class SetStatisticsRoundUserCase @Inject constructor(private val storage: DataStoreStorage) {

    suspend operator fun invoke(round: Int) {
        storage.setStatisticsRound(round)
    }
}
