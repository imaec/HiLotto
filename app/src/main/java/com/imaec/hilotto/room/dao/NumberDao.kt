package com.imaec.hilotto.room.dao

import androidx.room.Dao
import androidx.room.Insert
import com.imaec.hilotto.room.entity.NumberEntity

@Dao
interface NumberDao {

    @Insert
    fun insert(entity: NumberEntity)
}