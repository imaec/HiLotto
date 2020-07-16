package com.imaec.hilotto.model

import java.util.*

data class ContinueDTO(
    var no: String,
    var list: ArrayList<String>,
    var content: String,
    var continueNum: Array<ArrayList<Int>>
)