package com.imaec.domain.usecase.preferences

import com.imaec.domain.prefs.DataStoreStorage
import javax.inject.Inject

class SetLocalCurDrwNoUserCase @Inject constructor(private val storage: DataStoreStorage) {

    suspend operator fun invoke(curDrwNo: Int) {
        storage.setCurDrwNo(curDrwNo)
    }
}
