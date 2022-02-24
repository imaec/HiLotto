package com.imaec.domain.usecase.preferences

import com.imaec.domain.prefs.DataStoreStorage
import javax.inject.Inject

class GetRecommendPickCheckUseCase @Inject constructor(private val storage: DataStoreStorage) {

    suspend operator fun invoke() = storage.isRecommendPickCheck()
}
