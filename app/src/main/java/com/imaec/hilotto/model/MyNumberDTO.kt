package com.imaec.hilotto.model

import java.io.Serializable

data class MyNumberDTO(
    var numberId: Long = 0,
    val number1: Int = 0,
    val number2: Int = 0,
    val number3: Int = 0,
    val number4: Int = 0,
    val number5: Int = 0,
    val number6: Int = 0,
    val fitNumber: FitNumberDTO? = null
) : Serializable
