package com.imaec.domain.prefs

interface DataStoreStorage {

    suspend fun curDrwNo(): Int
    suspend fun setCurDrwNo(curDrwNo: Int)

    suspend fun isRecommendSumCheck(): Boolean
    suspend fun setRecommendSumCheck(isCheck: Boolean)

    suspend fun isRecommendPickCheck(): Boolean
    suspend fun setRecommendPickCheck(isCheck: Boolean)

    suspend fun isRecommendOddEvenCheck(): Boolean
    suspend fun setRecommendOddEvenCheck(isCheck: Boolean)

    suspend fun isRecommendAllCheck(): Boolean
    suspend fun setRecommendAllCheck(isCheck: Boolean)

    suspend fun statisticsRound(): Int
    suspend fun setStatisticsRound(round: Int)
}
