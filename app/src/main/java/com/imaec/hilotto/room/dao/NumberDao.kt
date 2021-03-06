package com.imaec.hilotto.room.dao

import androidx.room.*
import com.imaec.hilotto.room.entity.NumberEntity

@Dao
interface NumberDao {

    @Query("SELECT COUNT(*) FROM numberEntity WHERE number1 = :number1 AND number2 = :number2 AND number3 = :number3 AND number4 = :number4 AND number5 = :number5 AND number6 = :number6")
    fun selectByNumbers(number1: Int, number2: Int, number3: Int, number4: Int, number5: Int, number6: Int): Int

    @Query("SELECT * FROM numberEntity")
    fun selectAll(): List<NumberEntity>

    @Insert
    fun insert(entity: NumberEntity)

    @Insert
    fun insertAll(entities: List<NumberEntity>)

    @Update
    fun update(entity: NumberEntity): Int

    @Delete
    fun delete(entity: NumberEntity)
}