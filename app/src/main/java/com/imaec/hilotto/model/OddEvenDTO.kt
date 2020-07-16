package com.imaec.hilotto.model

import java.util.*

data class OddEvenDTO(
    var no: String,
    var listOdd: ArrayList<String>,
    var listEven: ArrayList<String>,
    var listOddEven: ArrayList<String>,
    var content: String
)