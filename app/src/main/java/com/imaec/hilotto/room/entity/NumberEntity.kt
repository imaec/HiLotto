package com.imaec.hilotto.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "numberEntity")
data class NumberEntity(
    @PrimaryKey(autoGenerate = true) var numberId: Int = 0,
    @ColumnInfo var number1: Int = 0,
    @ColumnInfo var number2: Int = 0,
    @ColumnInfo var number3: Int = 0,
    @ColumnInfo var number4: Int = 0,
    @ColumnInfo var number5: Int = 0,
    @ColumnInfo var number6: Int = 0
): Serializable