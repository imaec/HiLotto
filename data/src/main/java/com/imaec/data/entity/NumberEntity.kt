package com.imaec.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.imaec.domain.model.MyNumberDto

@Entity(tableName = "numberEntity")
data class NumberEntity(
    @PrimaryKey(autoGenerate = true) var numberId: Long = 0,
    @ColumnInfo var number1: Int = 0,
    @ColumnInfo var number2: Int = 0,
    @ColumnInfo var number3: Int = 0,
    @ColumnInfo var number4: Int = 0,
    @ColumnInfo var number5: Int = 0,
    @ColumnInfo var number6: Int = 0
) {

    companion object {
        fun toDto(entity: NumberEntity) = MyNumberDto(
            numberId = entity.numberId,
            number1 = entity.number1,
            number2 = entity.number2,
            number3 = entity.number3,
            number4 = entity.number4,
            number5 = entity.number5,
            number6 = entity.number6
        )

        fun fromDto(dto: MyNumberDto) = NumberEntity(
            numberId = dto.numberId,
            number1 = dto.number1,
            number2 = dto.number2,
            number3 = dto.number3,
            number4 = dto.number4,
            number5 = dto.number5,
            number6 = dto.number6
        )
    }
}
