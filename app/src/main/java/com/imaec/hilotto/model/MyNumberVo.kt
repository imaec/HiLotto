package com.imaec.hilotto.model

import com.imaec.domain.model.MyNumberDto
import java.io.Serializable

data class MyNumberVo(
    var numberId: Long = 0,
    val number1: Int = 0,
    val number2: Int = 0,
    val number3: Int = 0,
    val number4: Int = 0,
    val number5: Int = 0,
    val number6: Int = 0,
    val fitNumber: FitNumberVo? = null
) : Serializable {
    companion object {
        fun dtoToVo(dto: MyNumberDto) = MyNumberVo(
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
