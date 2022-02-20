package com.imaec.hilotto.model

data class ContinueDTO(
    var no: String,
    var list: List<String>,
    var content: String,
    var continueNum: Array<ArrayList<Int>>
)
