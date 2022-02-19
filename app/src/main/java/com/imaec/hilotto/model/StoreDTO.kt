package com.imaec.hilotto.model

import java.io.Serializable

data class StoreDTO(
    var storeName: String = "",
    var auto: String = "",
    var address: String = ""
) : Serializable
