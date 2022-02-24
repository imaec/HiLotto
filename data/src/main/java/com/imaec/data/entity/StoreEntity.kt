package com.imaec.data.entity

import java.io.Serializable

data class StoreEntity(
    var storeName: String = "",
    var auto: String = "",
    var address: String = ""
) : Serializable
