package com.imaec.domain.model

import java.io.Serializable

data class StoreDto(
    var storeName: String = "",
    var auto: String = "",
    var address: String = ""
) : Serializable
