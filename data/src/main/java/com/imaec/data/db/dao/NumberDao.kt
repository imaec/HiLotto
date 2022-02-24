package com.imaec.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.imaec.data.entity.NumberEntity

@Dao
interface NumberDao {

    @Query(
        "SELECT COUNT(*) FROM numberEntity WHERE number1 = :number1 " +
            "AND number2 = :number2 AND number3 = :number3 AND number4 = :number4 " +
            "AND number5 = :number5 AND number6 = :number6"
    )
    fun selectByNumbers(
        number1: Int,
        number2: Int,
        number3: Int,
        number4: Int,
        number5: Int,
        number6: Int
    ): Int

    @Query("SELECT * FROM numberEntity")
    fun selectAll(): LiveData<List<NumberEntity>>

    @Query("SELECT * FROM numberEntity")
    fun selectAllList(): List<NumberEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: NumberEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<NumberEntity>)

    @Update
    fun update(entity: NumberEntity): Int

    @Delete
    fun delete(entity: NumberEntity)

    @Query("DELETE FROM numberEntity WHERE numberId = :numberId")
    fun deleteById(numberId: Long)
}
